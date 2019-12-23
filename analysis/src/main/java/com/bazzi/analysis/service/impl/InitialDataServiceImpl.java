package com.bazzi.analysis.service.impl;

import com.bazzi.analysis.bean.StrategyConfig;
import com.bazzi.analysis.mapper.MonitorMapper;
import com.bazzi.analysis.mapper.ProjectHostMapper;
import com.bazzi.analysis.mapper.ProjectMapper;
import com.bazzi.analysis.model.Monitor;
import com.bazzi.analysis.model.Project;
import com.bazzi.analysis.service.InitialDataService;
import com.bazzi.analysis.service.RedisService;
import com.bazzi.analysis.util.Constant;
import com.bazzi.analysis.util.StrategyCache;
import com.bazzi.common.generic.NotifyAnalysis;
import com.bazzi.common.util.DateUtil;
import com.bazzi.common.util.GenericConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.ReentrantLock;

import static com.bazzi.analysis.util.StatisticalData.*;

@Slf4j
@Component
public class InitialDataServiceImpl implements InitialDataService {
    //开启同步统计数据到redis的锁
    private static volatile ReentrantLock syncRedisLock = new ReentrantLock();
    //是否已开启统计数据同步
    private static volatile boolean isStartSyncTotalData = false;

    //同步项目统计数据锁，防止更新配置时和同步线程发生竞争
    private static volatile ReentrantLock syncProjectLock = new ReentrantLock(true);

    @Resource
    private ProjectMapper projectMapper;
    @Resource
    private MonitorMapper monitorMapper;
    @Resource
    private ProjectHostMapper projectHostMapper;

    @Resource
    private RedisService redisService;

    public void startSyncTotalData() {
        syncRedisLock.lock();
        try {
            if (!isStartSyncTotalData) {
                syncTotalData();
                syncProjectData();
                setIsStartSyncTotalData();
            }
        } finally {
            syncRedisLock.unlock();
        }
    }

    private static void setIsStartSyncTotalData(){
        isStartSyncTotalData = true;
    }

    public void loadProjectConfig() {
        Project conditionProject = new Project();
        conditionProject.setStatus(0);//状态为有效
        List<Project> projectList = projectMapper.select(conditionProject);
        Monitor conditionMonitor = new Monitor();
        conditionMonitor.setStatus(0);//状态为有效
        List<Monitor> monitorList = monitorMapper.select(conditionMonitor);

        for (Project project : projectList) {
            String projectKey = project.getLogFileName();
            projectLogCountMap.put(projectKey, new LongAdder());
            projectLogTrafficMap.put(projectKey, new LongAdder());
            projectMonitorMap.put(projectKey, new LongAdder());
            projectAlarmMap.put(projectKey, new LongAdder());

            List<StrategyConfig> list = new ArrayList<>();
            for (Monitor monitor : monitorList) {
                if (monitor.getProjectId().intValue() == project.getId().intValue()) {
                    StrategyConfig sc = new StrategyConfig();
                    BeanUtils.copyProperties(project, sc);
                    BeanUtils.copyProperties(monitor, sc);
                    sc.setMonitorId(monitor.getId());
                    sc.initConfig();
                    list.add(sc);

                    initDetailMap(monitor, project);
                }
            }

            //有两个或者更多数据，则按照优先级排序
            if (list.size() > 1) {
                list.sort(Comparator.comparingInt(StrategyConfig::getPriority));
            }

            StrategyCache.put(projectKey, list, project);
        }
    }

    public void updateProjectConfig(NotifyAnalysis notifyAnalysis) {
        int projectId = notifyAnalysis.getProjectId();
        Project conditionProject = new Project();
        conditionProject.setStatus(0);//状态为有效
        conditionProject.setId(projectId);
        Project p = projectMapper.selectOne(conditionProject);
        Monitor conditionMonitor = new Monitor();
        conditionMonitor.setStatus(0);//状态为有效
        conditionMonitor.setProjectId(projectId);
        List<Monitor> monitorList = monitorMapper.select(conditionMonitor);

        String projectKey = p != null ? p.getLogFileName() : notifyAnalysis.getLogFileName();

        //移除配置
        StrategyCache.remove(projectKey);

        //如果是已存在项目，则同步数据、清理原有统计信息
        if (StrategyCache.containsProject(projectKey)) {
            clearByProjectKey(projectKey);
            StrategyCache.removeProject(projectKey);
        }

        //如果是删除项目，则操作结束
        if (p == null)
            return;

        projectLogCountMap.put(projectKey, new LongAdder());
        projectLogTrafficMap.put(projectKey, new LongAdder());
        projectMonitorMap.put(projectKey, new LongAdder());
        projectAlarmMap.put(projectKey, new LongAdder());

        List<StrategyConfig> list = new ArrayList<>();
        for (Monitor monitor : monitorList) {
            StrategyConfig sc = new StrategyConfig();
            BeanUtils.copyProperties(p, sc);
            BeanUtils.copyProperties(monitor, sc);
            sc.setMonitorId(monitor.getId());
            sc.initConfig();
            list.add(sc);

            initDetailMap(monitor, p);
        }

        //有两个或者更多数据，则按照优先级排序
        if (list.size() > 1) {
            list.sort(Comparator.comparingInt(StrategyConfig::getPriority));
        }

        StrategyCache.put(projectKey, list, p);
    }

    /**
     * 清除现有的基于项目的统计信息，清除之前先把数据同步到redis
     *
     * @param projectKey 项目标识
     */
    private void clearByProjectKey(String projectKey) {
        syncProjectHandler();

        projectLogCountMap.remove(projectKey);
        projectLogTrafficMap.remove(projectKey);
        projectMonitorMap.remove(projectKey);
        projectAlarmMap.remove(projectKey);

        projectDetailMonitorMap.remove(projectKey);
        projectDetailAlarmMap.remove(projectKey);
    }

    /**
     * 初始化策略命中、报警数统计map的数据
     *
     * @param monitor 策略
     * @param project 项目
     */
    private void initDetailMap(Monitor monitor, Project project) {
        String projectKey = project.getLogFileName();
        String key = String.format(Constant.DETAIL_FORMAT, monitor.getId(), monitor.getAlarmId());
        //项目存在，则将策略id放进去，否则创建项目
        ConcurrentHashMap<String, LongAdder> monitorMap =
                projectDetailMonitorMap.containsKey(projectKey) ?
                        projectDetailMonitorMap.get(projectKey) : new ConcurrentHashMap<>();
        //项目存在，则将报警id放进去，否则创建项目
        ConcurrentHashMap<String, LongAdder> alarmMap =
                projectDetailAlarmMap.containsKey(projectKey) ?
                        projectDetailAlarmMap.get(projectKey) : new ConcurrentHashMap<>();
        monitorMap.put(key, new LongAdder());
        alarmMap.put(key, new LongAdder());
        projectDetailMonitorMap.put(projectKey, monitorMap);
        projectDetailAlarmMap.put(projectKey, alarmMap);
    }

    /**
     * 开启一个线程，同步总计的各项数据
     */
    private void syncTotalData() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            do {
                Date currentDate = new Date();
                String ymd = DateUtil.getYmd(currentDate);
                String hour = DateUtil.getHH(currentDate);
                String statisticalKey = String.format(GenericConst.RTS_STATISTICAL_KEY, ymd);
                String totalKey = String.format(GenericConst.RTS_TOTAL_KEY, ymd, hour);

                long logTrafficNum = totalLogTraffic.longValue();
                long logCountNum = totalLogCount.longValue();
                long monitorNum = totalMonitor.longValue();
                long alarmNum = totalAlarm.longValue();

                redisService.hIncrBy(statisticalKey, GenericConst.TOTAL_LOG_TRAFFIC, logTrafficNum);
                redisService.hIncrBy(statisticalKey, GenericConst.TOTAL_LOG_COUNT, logCountNum);
                redisService.hIncrBy(statisticalKey, GenericConst.TOTAL_MONITOR, monitorNum);
                redisService.hIncrBy(statisticalKey, GenericConst.TOTAL_ALARM, alarmNum);

                redisService.hIncrBy(totalKey, GenericConst.TOTAL_LOG_TRAFFIC, logTrafficNum);
                redisService.hIncrBy(totalKey, GenericConst.TOTAL_LOG_COUNT, logCountNum);
                redisService.hIncrBy(totalKey, GenericConst.TOTAL_MONITOR, monitorNum);
                redisService.hIncrBy(totalKey, GenericConst.TOTAL_ALARM, alarmNum);
                log.info("统计信息同步线程ID为:{},进行一次同步,日志字节数:{},日志条数:{},策略命中数:{},报警发生数:{}",
                        Thread.currentThread().getId(), logTrafficNum, logCountNum, monitorNum, alarmNum);

                totalLogTraffic.add(-logTrafficNum);
                totalLogCount.add(-logCountNum);
                totalMonitor.add(-monitorNum);
                totalAlarm.add(-alarmNum);

                try {
                    Thread.sleep(Constant.SYNC_INTERVAL_MILLIS);
                } catch (InterruptedException e) {
                    log.error(e.getMessage(), e);
                }
            } while (true);
        });
    }

    /**
     * 开启一个线程，同步项目的各项数据，同步间隔一分钟
     */
    private void syncProjectData() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            do {
                syncProjectHandler();

                try {
                    Thread.sleep(Constant.SYNC_PROJECT_INTERVAL_MILLIS);
                } catch (InterruptedException e) {
                    log.error(e.getMessage(), e);
                }
            } while (true);
        });
    }

    /**
     * 同步项目的各项数据的具体操作
     */
    private void syncProjectHandler() {
        syncProjectLock.lock();
        try {
            Date currentDate = new Date();
            String ymd = DateUtil.getYmd(currentDate);
            String hour = DateUtil.getHH(currentDate);

            //基于每个项目的流量、记录数、命中数、报警数据进行一次同步
            for (String key : projectLogCountMap.keySet()) {
                Integer projectId = StrategyCache.getProjectId(key);
                String projectTotalKey = String.format(GenericConst.RTS_PROJECT_KEY, ymd, hour, projectId);

                LongAdder projectLogTraffic = projectLogTrafficMap.get(key);
                LongAdder projectLogCount = projectLogCountMap.get(key);
                LongAdder projectMonitor = projectMonitorMap.get(key);
                LongAdder projectAlarm = projectAlarmMap.get(key);

                long logTrafficNum = projectLogTraffic.longValue();
                long logCountNum = projectLogCount.longValue();
                long monitorNum = projectMonitor.longValue();
                long alarmNum = projectAlarm.longValue();

                redisService.hIncrBy(projectTotalKey, GenericConst.LOG_TRAFFIC, logTrafficNum);
                redisService.hIncrBy(projectTotalKey, GenericConst.LOG_COUNT, logCountNum);
                redisService.hIncrBy(projectTotalKey, GenericConst.MONITOR, monitorNum);
                redisService.hIncrBy(projectTotalKey, GenericConst.ALARM, alarmNum);
                log.info("项目---统计信息同步线程ID为:{},对ID为:{}的项目进行一次同步,日志字节数:{},日志条数:{},策略命中数:{},报警发生数:{}",
                        Thread.currentThread().getId(), projectId, logTrafficNum, logCountNum, monitorNum, alarmNum);

                projectLogTraffic.add(-logTrafficNum);
                projectLogCount.add(-logCountNum);
                projectMonitor.add(-monitorNum);
                projectAlarm.add(-alarmNum);
            }

            //monitor与alarm有一对一的对应关系
            //同步每个项目的策略对应的命中数、每个项目的报警对应的报警次数
            for (String key : projectDetailMonitorMap.keySet()) {
                Integer projectId = StrategyCache.getProjectId(key);
                String detailKey = String.format(GenericConst.RTS_DETAIL_KEY, ymd, hour, projectId);

                ConcurrentHashMap<String, LongAdder> monitorMap = projectDetailMonitorMap.get(key);
                ConcurrentHashMap<String, LongAdder> alarmMap = projectDetailAlarmMap.get(key);
                for (String monitorAlarmKey : monitorMap.keySet()) {
                    String[] idArr = monitorAlarmKey.split(Constant.DETAIL_DELIMITER);
                    Integer monitorId = Integer.parseInt(idArr[0]);
                    Integer alarmId = Integer.parseInt(idArr[1]);

                    LongAdder monitorVal = monitorMap.get(monitorAlarmKey);
                    LongAdder alarmVal = alarmMap.get(monitorAlarmKey);

                    long monitorNum = monitorVal.longValue();
                    long alarmNum = alarmVal.longValue();

                    String monitorName = String.format(GenericConst.DETAIL_MONITOR, monitorId, alarmId);
                    String alarmName = String.format(GenericConst.DETAIL_ALARM, monitorId, alarmId);

                    redisService.hIncrBy(detailKey, monitorName, monitorNum);
                    redisService.hIncrBy(detailKey, alarmName, alarmNum);
                    log.info("项目明细---统计信息同步线程ID为:{},对ID为:{}的项目进行一次同步,策略ID:{},策略命中数:{},报警ID:{},报警发生数:{}",
                            Thread.currentThread().getId(), projectId, monitorId, monitorNum, alarmId, alarmNum);

                    monitorVal.add(-monitorNum);
                    alarmVal.add(-alarmNum);
                }

            }
        } finally {
            syncProjectLock.unlock();
        }
    }

}

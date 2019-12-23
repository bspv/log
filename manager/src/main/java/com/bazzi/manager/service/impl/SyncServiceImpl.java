package com.bazzi.manager.service.impl;

import com.bazzi.common.util.DateUtil;
import com.bazzi.common.util.GenericConst;
import com.bazzi.manager.config.DefinitionProperties;
import com.bazzi.manager.mapper.StatisticalDetailMapper;
import com.bazzi.manager.mapper.StatisticalMapper;
import com.bazzi.manager.mapper.StatisticalMonitorMapper;
import com.bazzi.manager.mapper.StatisticalProjectMapper;
import com.bazzi.manager.model.Statistical;
import com.bazzi.manager.model.StatisticalDetail;
import com.bazzi.manager.model.StatisticalMonitor;
import com.bazzi.manager.model.StatisticalProject;
import com.bazzi.manager.service.RedisService;
import com.bazzi.manager.service.SyncService;
import com.bazzi.manager.util.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

@Slf4j
@Component
public class SyncServiceImpl implements SyncService {
    @Resource
    private RedisService redisService;

    @Resource
    private StatisticalMapper statisticalMapper;
    @Resource
    private StatisticalDetailMapper statisticalDetailMapper;
    @Resource
    private StatisticalProjectMapper statisticalProjectMapper;
    @Resource
    private StatisticalMonitorMapper statisticalMonitorMapper;

    @Resource
    private DefinitionProperties definitionProperties;

    public void syncTotal() {
        String lockKey = String.format(Constant.SYNC_TOTAL_LOCK_KEY, definitionProperties.getCachePrefix());
        String lockVal = UUID.randomUUID().toString();
        //获取锁失败，则直接结束
        if (!redisService.lock(lockKey, lockVal, Constant.SYNC_LOCK_TIME))
            return;

        try {
            String curDay = DateUtil.getYmd(new Date());

            Set<String> keys = redisService.keys(GenericConst.RTS_STATISTICAL_PATTERN);
            if (keys != null && keys.size() > 0) {
                syncTotalByDay(keys, curDay);
            }

            Set<String> totalKeys = redisService.keys(GenericConst.RTS_TOTAL_PATTERN);
            if (totalKeys != null && totalKeys.size() > 0) {
                syncTotalByHour(totalKeys, curDay);
            }

        } finally {
            redisService.releaseLock(lockKey, lockVal);
        }
    }

    public void syncProject() {
        String lockKey = String.format(Constant.SYNC_PROJECT_LOCK_KEY, definitionProperties.getCachePrefix());
        String lockVal = UUID.randomUUID().toString();
        //获取锁失败，则直接结束
        if (!redisService.lock(lockKey, lockVal, Constant.SYNC_LOCK_TIME))
            return;

        try {

            Date curDate = new Date();
            String curDayHour = DateUtil.getYmd(curDate) + ":" + DateUtil.getHH(curDate);

            Set<String> keys = redisService.keys(GenericConst.RTS_PROJECT_PATTERN);
            if (keys != null && keys.size() > 0) {
                syncProjectByHour(keys, curDayHour);
            }


            Set<String> detailKeys = redisService.keys(GenericConst.RTS_DETAIL_PATTERN);
            if (detailKeys != null && detailKeys.size() > 0) {
                syncMonitorByHour(detailKeys, curDayHour);
            }

        } finally {
            redisService.releaseLock(lockKey, lockVal);
        }
    }

    /**
     * 同步按天统计的总计数据，不同步当天的
     *
     * @param keys   key集合
     * @param curDay 当天yyyy-MM-dd
     */
    private void syncTotalByDay(Set<String> keys, String curDay) {
        List<String> delKeys = new ArrayList<>(keys);
        List<Statistical> list = new ArrayList<>();
        for (String key : keys) {
            String day = key.replace(GenericConst.RTS_STATISTICAL_PREFIX, "");
            if (curDay.equals(day)) {
                delKeys.remove(key);
                continue;
            }
            buildStatistical(redisService.hGetAll(key), day, list);
        }
        if (list.size() > 0) {
            list.sort(this::compareStatistical);
            statisticalMapper.insertList(list);
        }
        redisService.delByPipeline(delKeys);
    }

    /**
     * 同步按小时统计的总计数据，不同步当天的
     *
     * @param totalKeys key集合
     * @param curDay    当天yyyy-MM-dd
     */
    private void syncTotalByHour(Set<String> totalKeys, String curDay) {
        List<String> delTotalKeys = new ArrayList<>(totalKeys);
        List<StatisticalDetail> totalList = new ArrayList<>();
        for (String key : totalKeys) {
            String dayHour = key.replace(GenericConst.RTS_TOTAL_PREFIX, "");
            if (dayHour.contains(curDay)) {
                delTotalKeys.remove(key);
                continue;
            }
            buildStatisticalDetail(redisService.hGetAll(key), dayHour, totalList);
        }
        if (totalList.size() > 0) {
            totalList.sort(this::compareStatisticalDetail);
            statisticalDetailMapper.insertList(totalList);
        }
        redisService.delByPipeline(delTotalKeys);
    }

    /**
     * 根据redis中信息构建Statistical对象，并放入集合
     *
     * @param map  redis里获取的数据
     * @param day  天，yyyy-MM-dd
     * @param list 集合
     */
    private void buildStatistical(Map<Object, Object> map, String day, List<Statistical> list) {
        Statistical s = new Statistical();
        s.setTotalLogTraffic(Long.parseLong(String.valueOf(map.get(GenericConst.TOTAL_LOG_TRAFFIC))));
        s.setTotalLogCount(Long.parseLong(String.valueOf(map.get(GenericConst.TOTAL_LOG_COUNT))));
        s.setTotalMonitorNum(Long.parseLong(String.valueOf(map.get(GenericConst.TOTAL_MONITOR))));
        s.setTotalAlarmNum(Long.parseLong(String.valueOf(map.get(GenericConst.TOTAL_ALARM))));
        s.setTotalDay(day);
        s.setCreateTime(new Date());
        list.add(s);
    }

    /**
     * 根据redis中信息构建StatisticalDetail对象，并放入集合
     *
     * @param map       redis里获取的数据
     * @param dayHour   时间，yyyy-MM-dd:HH
     * @param totalList 集合
     */
    private void buildStatisticalDetail(Map<Object, Object> map, String dayHour, List<StatisticalDetail> totalList) {
        StatisticalDetail sd = new StatisticalDetail();
        String[] arr = dayHour.split(":");
        String day = arr[0];
        String hour = arr[1];
        String totalTime = dayHour.replace(":", "").replace("-", "");
        sd.setTotalLogTraffic(Long.parseLong(String.valueOf(map.get(GenericConst.TOTAL_LOG_TRAFFIC))));
        sd.setTotalLogCount(Long.parseLong(String.valueOf(map.get(GenericConst.TOTAL_LOG_COUNT))));
        sd.setTotalMonitorNum(Long.parseLong(String.valueOf(map.get(GenericConst.TOTAL_MONITOR))));
        sd.setTotalAlarmNum(Long.parseLong(String.valueOf(map.get(GenericConst.TOTAL_ALARM))));
        sd.setTotalDay(day);
        sd.setTotalHour(hour);
        sd.setTotalTime(totalTime);
        sd.setCreateTime(new Date());
        totalList.add(sd);
    }

    /**
     * 按照Statistical的totalDay升序
     *
     * @param s1 元素1
     * @param s2 元素2
     * @return 比较结果
     */
    private int compareStatistical(Statistical s1, Statistical s2) {
        String totalDay1 = s1.getTotalDay().replace("-", "");
        String totalDay2 = s2.getTotalDay().replace("-", "");
        return Integer.parseInt(totalDay1) - Integer.parseInt(totalDay2);
    }

    /**
     * 按照StatisticalDetail的totalTime升序
     *
     * @param sd1 元素1
     * @param sd2 元素2
     * @return 比较结果
     */
    private int compareStatisticalDetail(StatisticalDetail sd1, StatisticalDetail sd2) {
        return Integer.parseInt(sd1.getTotalTime()) - Integer.parseInt(sd2.getTotalTime());
    }

    /**
     * 同步基于项目的按小时统计的数据(日志数、日志量、策略命中数、报警数)，不同步当前小时的数据
     *
     * @param keys       key集合
     * @param curDayHour 当前时间，yyyy-MM-dd:HH
     */
    private void syncProjectByHour(Set<String> keys, String curDayHour) {
        List<String> delProjectKeys = new ArrayList<>(keys);

        List<StatisticalProject> projectList = new ArrayList<>();
        for (String key : keys) {
            String dayHourProjectId = key.replace(GenericConst.RTS_PROJECT_PREFIX, "");
            if (dayHourProjectId.contains(curDayHour)) {
                delProjectKeys.remove(key);
                continue;
            }
            buildStatisticalProject(redisService.hGetAll(key), dayHourProjectId, projectList);
        }

        if (projectList.size() > 0) {
            projectList.sort(this::compareStatisticalProject);
            statisticalProjectMapper.insertList(projectList);
        }
        redisService.delByPipeline(delProjectKeys);
    }

    /**
     * 同步按小时统计的策略命中数、报警数，不同步当前小时的数据
     *
     * @param detailKeys key集合
     * @param curDayHour 当前时间，yyyy-MM-dd:HH
     */
    private void syncMonitorByHour(Set<String> detailKeys, String curDayHour) {
        List<String> delDetailKeys = new ArrayList<>(detailKeys);

        List<StatisticalMonitor> monitorList = new ArrayList<>();
        for (String key : detailKeys) {
            String dayHourProjectId = key.replace(GenericConst.RTS_DETAIL_PREFIX, "");
            if (dayHourProjectId.contains(curDayHour)) {
                delDetailKeys.remove(key);
                continue;
            }
            buildStatisticalMonitor(redisService.hGetAll(key), dayHourProjectId, monitorList);
        }

        if (monitorList.size() > 0) {
            monitorList.sort(this::compareStatisticalMonitor);
            statisticalMonitorMapper.insertList(monitorList);
        }
        redisService.delByPipeline(delDetailKeys);
    }

    /**
     * 根据redis中信息构建StatisticalProject对象，并放入集合
     *
     * @param map              redis里获取的数据
     * @param dayHourProjectId 时间，yyyy-MM-dd:HH:projectId
     * @param projectList      集合
     */
    private void buildStatisticalProject(Map<Object, Object> map, String dayHourProjectId,
                                         List<StatisticalProject> projectList) {
        StatisticalProject sp = new StatisticalProject();
        String[] arr = dayHourProjectId.split(":");
        String day = arr[0];
        String hour = arr[1];
        Integer projectId = Integer.parseInt(arr[2]);
        String totalTime = day.replace("-", "") + hour;

        sp.setProjectId(projectId);
        sp.setLogTraffic(Long.parseLong(String.valueOf(map.get(GenericConst.LOG_TRAFFIC))));
        sp.setLogCount(Long.parseLong(String.valueOf(map.get(GenericConst.LOG_COUNT))));
        sp.setMonitorNum(Long.parseLong(String.valueOf(map.get(GenericConst.MONITOR))));
        sp.setAlarmNum(Long.parseLong(String.valueOf(map.get(GenericConst.ALARM))));
        sp.setTotalDay(day);
        sp.setTotalHour(hour);
        sp.setTotalTime(totalTime);
        sp.setCreateTime(new Date());
        projectList.add(sp);
    }

    /**
     * 根据redis中信息构建StatisticalMonitor对象，并放入集合
     *
     * @param map              redis里获取的数据
     * @param dayHourProjectId 时间，yyyy-MM-dd:HH:projectId
     * @param monitorList      集合
     */
    private void buildStatisticalMonitor(Map<Object, Object> map, String dayHourProjectId,
                                         List<StatisticalMonitor> monitorList) {
        StatisticalMonitor pm = new StatisticalMonitor();
        String[] arr = dayHourProjectId.split(":");
        String day = arr[0];
        String hour = arr[1];
        Integer projectId = Integer.parseInt(arr[2]);
        String totalTime = day.replace("-", "") + hour;

        pm.setProjectId(projectId);
        pm.setTotalDay(day);
        pm.setTotalHour(hour);
        pm.setTotalTime(totalTime);

        for (Object o : map.keySet()) {
            String kk = String.valueOf(o);
            if (kk.contains(GenericConst.DETAIL_MONITOR_SUFFIX)) {
                long monitorNum = Long.parseLong(String.valueOf(map.get(kk)));
                String replace = kk.replace(GenericConst.DETAIL_MONITOR_SUFFIX, "");
                String[] idArr = replace.split("_");
                Integer monitorId = Integer.parseInt(idArr[0]);
                Integer alarmId = Integer.parseInt(idArr[1]);

                String alarmK = String.format(GenericConst.DETAIL_ALARM, monitorId, alarmId);
                long alarmNum = Long.parseLong(String.valueOf(map.get(alarmK)));

                StatisticalMonitor tmp = new StatisticalMonitor();
                BeanUtils.copyProperties(pm, tmp);
                tmp.setMonitorId(monitorId);
                tmp.setAlarmId(alarmId);
                tmp.setMonitorNum(monitorNum);
                tmp.setAlarmNum(alarmNum);
                tmp.setCreateTime(new Date());
                monitorList.add(tmp);
            }
        }
    }

    /**
     * 按照StatisticalProject的totalTime升序，如totalTime相同，按照projectId升序
     *
     * @param sp1 元素1
     * @param sp2 元素2
     * @return 比较结果
     */
    private int compareStatisticalProject(StatisticalProject sp1, StatisticalProject sp2) {
        String totalTime1 = sp1.getTotalTime();
        String totalTime2 = sp2.getTotalTime();
        if (!totalTime1.equals(totalTime2))
            return Integer.parseInt(totalTime1) - Integer.parseInt(totalTime2);
        return sp1.getProjectId() - sp2.getProjectId();
    }

    /**
     * 1.按照StatisticalMonitor的totalTime升序，
     * 2.如totalTime相同，按照projectId升序
     * 3.如projectId相同，按照monitorId升序
     * 4.如monitorId相同，按照alarmId升序
     *
     * @param e1 元素1
     * @param e2 元素2
     * @return 比较结果
     */
    private int compareStatisticalMonitor(StatisticalMonitor e1, StatisticalMonitor e2) {
        String totalTime1 = e1.getTotalTime();
        String totalTime2 = e2.getTotalTime();
        if (!totalTime1.equals(totalTime2))
            return Integer.parseInt(totalTime1) - Integer.parseInt(totalTime2);
        Integer projectId1 = e1.getProjectId();
        Integer projectId2 = e2.getProjectId();
        if (projectId1.intValue() != projectId2.intValue())
            return projectId1 - projectId2;
        Integer monitorId1 = e1.getMonitorId();
        Integer monitorId2 = e2.getMonitorId();
        if (monitorId1.intValue() != monitorId2.intValue())
            return monitorId1 - monitorId2;
        return e1.getAlarmId() - e2.getAlarmId();
    }

}

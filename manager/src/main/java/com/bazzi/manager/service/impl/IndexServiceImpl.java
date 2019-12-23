package com.bazzi.manager.service.impl;

import com.bazzi.common.util.DateUtil;
import com.bazzi.common.util.GenericConst;
import com.bazzi.manager.model.Project;
import com.bazzi.manager.service.IndexService;
import com.bazzi.manager.service.ProjectService;
import com.bazzi.manager.service.RedisService;
import com.bazzi.manager.util.ByteConvertUtil;
import com.bazzi.manager.vo.response.IndexResponseVO;
import com.bazzi.manager.vo.response.LogDetailHourVO;
import com.bazzi.manager.vo.response.ProjectTotalVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

@Slf4j
@Component
public class IndexServiceImpl implements IndexService {
    @Resource
    private RedisService redisService;

    @Resource
    private ProjectService projectService;

    public IndexResponseVO dashboard() {
        IndexResponseVO vo = new IndexResponseVO();
        Date currentDate = new Date();
        String ymd = DateUtil.getYmd(currentDate);
        String hour = DateUtil.getHH(currentDate);
        vo.setYmd(ymd);
        vo.setHour(hour);

        //获取当天总计，按天统计的数据
        String statisticalKey = String.format(GenericConst.RTS_STATISTICAL_KEY, ymd);
        Map<Object, Object> map = redisService.hGetAll(statisticalKey);
        if (map != null && map.size() > 0) {
            vo.setTotalLogTraffic(byteToKB(map.get(GenericConst.TOTAL_LOG_TRAFFIC)));
            vo.setTotalLogCount(Long.parseLong(String.valueOf(map.get(GenericConst.TOTAL_LOG_COUNT))));
            vo.setTotalMonitorNum(Long.parseLong(String.valueOf(map.get(GenericConst.TOTAL_MONITOR))));
            vo.setTotalAlarmNum(Long.parseLong(String.valueOf(map.get(GenericConst.TOTAL_ALARM))));
        }

        //获取当天按小时统计的数据
        findTotalByHour(vo, ymd);

        //获取当天按小时，基于每个项目统计的数据
        findProjectTotalList(vo, ymd, hour);

        return vo;
    }

    /**
     * 获取当天按小时统计的数据，按照统计时间正序，用于图表显示
     *
     * @param vo  返回VO
     * @param ymd 日期，yyyy-MM-dd
     */
    private void findTotalByHour(IndexResponseVO vo, String ymd) {
        List<LogDetailHourVO> detailHourList = new ArrayList<>();

        String totalKey = String.format(GenericConst.RTS_TOTAL_KEY, ymd, "*");
        Set<String> keys = redisService.keys(totalKey);
        if (keys != null && keys.size() > 0) {
            for (String key : keys) {
                String dayHour = key.replace(GenericConst.RTS_TOTAL_PREFIX, "");//yyyy-MM-dd:HH
                String[] arr = dayHour.split(":");
                String hh = arr[1];

                LogDetailHourVO logDetailHourVO = new LogDetailHourVO();

                Map<Object, Object> m = redisService.hGetAll(key);

                logDetailHourVO.setTotalLogCount(Long.parseLong(String.valueOf(m.get(GenericConst.TOTAL_LOG_COUNT))));
                logDetailHourVO.setTotalLogTraffic(byteToKB(m.get(GenericConst.TOTAL_LOG_TRAFFIC)));
                logDetailHourVO.setTotalMonitorNum(Long.parseLong(String.valueOf(m.get(GenericConst.TOTAL_MONITOR))));
                logDetailHourVO.setTotalAlarmNum(Long.parseLong(String.valueOf(m.get(GenericConst.TOTAL_ALARM))));
                logDetailHourVO.setHour(hh);

                detailHourList.add(logDetailHourVO);
            }
            detailHourList.sort(this::compareLogDetailHourVO);
        }

        vo.setDetailHourList(detailHourList);
    }

    /**
     * 获取当天按小时，基于每个项目统计的数据，按报警数、日志行数倒序，并取前10条（超过10条取10条，不然全部显示）
     *
     * @param vo   返回VO
     * @param ymd  日期，yyyy-MM-dd
     * @param hour 小时
     */
    private void findProjectTotalList(IndexResponseVO vo, String ymd, String hour) {
        List<ProjectTotalVO> projectTotalList = new ArrayList<>();
        String projectKey = String.format(GenericConst.RTS_PROJECT_KEY, ymd, hour, "*");
        Set<String> pKeys = redisService.keys(projectKey);
        if (pKeys != null && pKeys.size() > 0) {
            for (String pKey : pKeys) {
                ProjectTotalVO ptv = new ProjectTotalVO();
                Map<Object, Object> map1 = redisService.hGetAll(pKey);
                String dayHourProjectId = pKey.replace(GenericConst.RTS_PROJECT_PREFIX, "");//yyyy-MM-dd:HH:projectId
                String[] arr = dayHourProjectId.split(":");
                String day = arr[0];
                String hh = arr[1];
                Integer projectId = Integer.parseInt(arr[2]);
                Project p = projectService.findByIdWithCache(projectId);
                ptv.setProjectId(projectId);
                ptv.setProjectName(p.getProjectName());
                ptv.setLogTraffic(byteToKB(map1.get(GenericConst.LOG_TRAFFIC)));
                ptv.setLogCount(Long.parseLong(String.valueOf(map1.get(GenericConst.LOG_COUNT))));
                ptv.setMonitorNum(Long.parseLong(String.valueOf(map1.get(GenericConst.MONITOR))));
                ptv.setAlarmNum(Long.parseLong(String.valueOf(map1.get(GenericConst.ALARM))));
                ptv.setTotalTime(day + " " + hh);
                projectTotalList.add(ptv);
            }
            projectTotalList.sort(this::compareProjectTotalVO);
            projectTotalList = projectTotalList.subList(0, Math.min(10, projectTotalList.size()));
        }
        vo.setProjectTotalList(projectTotalList);
    }


    /**
     * 按照hour正序
     *
     * @param v1 LogDetailHourVO
     * @param v2 LogDetailHourVO
     * @return 比较结果
     */
    private int compareLogDetailHourVO(LogDetailHourVO v1, LogDetailHourVO v2) {
        return Integer.parseInt(v1.getHour()) - Integer.parseInt(v2.getHour());
    }

    /**
     * 先按照alarmNum倒序，如果相同，按照logCount倒序
     *
     * @param ptv1 项目统计
     * @param ptv2 项目统计
     * @return 比较结果
     */
    private int compareProjectTotalVO(ProjectTotalVO ptv1, ProjectTotalVO ptv2) {
        Long alarmNum1 = ptv1.getAlarmNum();
        Long alarmNum2 = ptv2.getAlarmNum();
        if (alarmNum1.longValue() != alarmNum2.longValue()) {
            return (int) (alarmNum2 - alarmNum1);
        }
        Long logCount1 = ptv1.getLogCount();
        Long logCount2 = ptv2.getLogCount();
        return (int) (logCount2 - logCount1);
    }

    /**
     * 将字节数转为KB，四舍五入，精确小数点后两位
     *
     * @param obj 字节数
     * @return KB值
     */
    private double byteToKB(Object obj) {
        return obj == null ? -1 : ByteConvertUtil.byteToKB(String.valueOf(obj));
    }
}

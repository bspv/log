package com.bazzi.manager.service;

import com.bazzi.common.generic.Page;
import com.bazzi.manager.vo.request.TotalByDayReqVO;
import com.bazzi.manager.vo.request.TotalByHourReqVO;
import com.bazzi.manager.vo.request.TotalByMonitorReqVO;
import com.bazzi.manager.vo.request.TotalByProjectReqVO;
import com.bazzi.manager.vo.response.TotalByDayResponseVO;
import com.bazzi.manager.vo.response.TotalByHourResponseVO;
import com.bazzi.manager.vo.response.TotalByMonitorResponseVO;
import com.bazzi.manager.vo.response.TotalByProjectResponseVO;

public interface StatisticalService {
    /**
     * 分页查询按天统计的总计数据
     *
     * @param totalByDayReqVO 查询条件
     * @return 列表结果
     */
    Page<TotalByDayResponseVO> listTotalByDay(TotalByDayReqVO totalByDayReqVO);

    /**
     * 分页查询按小时统计的总计数据
     *
     * @param totalByHourReqVO 查询条件
     * @return 列表结果
     */
    Page<TotalByHourResponseVO> listTotalByHour(TotalByHourReqVO totalByHourReqVO);

    /**
     * 分页查询基于项目、按小时统计的数据
     *
     * @param totalByProjectReqVO 查询条件
     * @return 列表结果
     */
    Page<TotalByProjectResponseVO> listTotalByProject(TotalByProjectReqVO totalByProjectReqVO);

    /**
     * 分页查询基于监控策略、按小时统计的数据
     *
     * @param totalByMonitorReqVO 查询条件
     * @return 列表结果
     */
    Page<TotalByMonitorResponseVO> listTotalByMonitor(TotalByMonitorReqVO totalByMonitorReqVO);
}

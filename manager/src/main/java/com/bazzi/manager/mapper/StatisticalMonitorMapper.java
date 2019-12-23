package com.bazzi.manager.mapper;

import com.bazzi.manager.bean.StatisticalMonitorDO;
import com.bazzi.manager.model.StatisticalMonitor;
import com.bazzi.manager.mybatis.BaseMapper;
import com.bazzi.manager.vo.request.TotalByMonitorReqVO;

import java.util.List;

public interface StatisticalMonitorMapper extends BaseMapper<StatisticalMonitor> {
    List<StatisticalMonitorDO> selectByParams(TotalByMonitorReqVO reqVO);
}
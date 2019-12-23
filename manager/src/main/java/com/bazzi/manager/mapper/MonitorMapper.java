package com.bazzi.manager.mapper;

import com.bazzi.manager.model.Monitor;
import com.bazzi.manager.mybatis.BaseMapper;
import com.bazzi.manager.vo.request.MonitorListReqVO;

import java.util.List;

public interface MonitorMapper extends BaseMapper<Monitor> {
    List<Monitor> selectByParams(MonitorListReqVO monitorListReqVO);

    int update(Monitor p);
}

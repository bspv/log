package com.bazzi.manager.mapper;

import com.bazzi.manager.bean.StatisticalProjectDO;
import com.bazzi.manager.model.StatisticalProject;
import com.bazzi.manager.mybatis.BaseMapper;
import com.bazzi.manager.vo.request.TotalByProjectReqVO;

import java.util.List;

public interface StatisticalProjectMapper extends BaseMapper<StatisticalProject> {
    List<StatisticalProjectDO> selectByParams(TotalByProjectReqVO reqVO);
}
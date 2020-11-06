package com.bazzi.manager.mapper;

import com.bazzi.common.mybatis.BaseMapper;
import com.bazzi.manager.model.StatisticalDetail;
import com.bazzi.manager.vo.request.TotalByHourReqVO;

import java.util.List;

public interface StatisticalDetailMapper extends BaseMapper<StatisticalDetail> {
    List<StatisticalDetail> selectByParams(TotalByHourReqVO reqVO);
}
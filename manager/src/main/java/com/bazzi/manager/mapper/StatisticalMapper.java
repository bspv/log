package com.bazzi.manager.mapper;

import com.bazzi.manager.model.Statistical;
import com.bazzi.manager.mybatis.BaseMapper;
import com.bazzi.manager.vo.request.TotalByDayReqVO;

import java.util.List;

public interface StatisticalMapper extends BaseMapper<Statistical> {
    List<Statistical> selectByParams(TotalByDayReqVO reqVO);
}
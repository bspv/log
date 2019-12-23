package com.bazzi.manager.mapper;

import com.bazzi.manager.model.AlarmRecord;
import com.bazzi.manager.mybatis.BaseMapper;
import com.bazzi.manager.vo.request.AlarmRecordReqVO;

import java.util.List;

public interface AlarmRecordMapper extends BaseMapper<AlarmRecord> {
    List<AlarmRecord> selectByParams(AlarmRecordReqVO alarmRecordReqVO);
}

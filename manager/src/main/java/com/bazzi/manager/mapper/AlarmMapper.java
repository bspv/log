package com.bazzi.manager.mapper;

import com.bazzi.common.mybatis.BaseMapper;
import com.bazzi.manager.model.Alarm;
import com.bazzi.manager.vo.request.AlarmListReqVO;

import java.util.List;

public interface AlarmMapper  extends BaseMapper<Alarm> {
    List<Alarm> selectAlarmList(AlarmListReqVO alarmListReqVO);

    int update(Alarm alarm);

    Alarm selectCheckAgain(Alarm alarm);

    int updateStatus(Alarm alarm);
}

package com.bazzi.manager.mapper;

import com.bazzi.manager.model.AlarmGroup;
import com.bazzi.manager.mybatis.BaseMapper;
import com.bazzi.manager.vo.request.AlarmGroupListReqVO;

import java.util.List;

public interface AlarmGroupMapper extends BaseMapper<AlarmGroup> {
    List<AlarmGroup> selectAlarmGroupList(AlarmGroupListReqVO alarmGroupListReqVO);

    int update(AlarmGroup alarmGroup);

    AlarmGroup selectCheckAgain(AlarmGroup alarmGroup);

    int updateStatus(AlarmGroup alarmGroup);
}

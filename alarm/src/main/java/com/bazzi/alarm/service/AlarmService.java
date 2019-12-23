package com.bazzi.alarm.service;

import com.bazzi.common.generic.AlarmDesc;

public interface AlarmService {
    /**
     * 根据报警详情，查询对应配置，然后发送报警，并写入报警记录表、报警记录详情表
     *
     * @param alarmDesc 报警详情
     */
    void alarm(AlarmDesc alarmDesc);
}

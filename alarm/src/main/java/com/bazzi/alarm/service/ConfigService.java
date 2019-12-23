package com.bazzi.alarm.service;

import com.bazzi.alarm.model.*;
import com.bazzi.common.generic.NotifyAlarm;

import java.util.List;

public interface ConfigService {

    /**
     * 根据id查询项目
     *
     * @param id 项目id
     * @return 项目信息
     */
    Project findProjectById(Integer id);

    /**
     * 根据id查询监控策略
     *
     * @param id 监控策略id
     * @return 监控策略信息
     */
    Monitor findMonitorById(Integer id);

    /**
     * 根据项目id查询项目字段信息
     *
     * @param projectId 项目id
     * @return 项目字段信息集合
     */
    List<ProjectField> findProjectFieldByProjectId(Integer projectId);

    /**
     * 根据id查询报警配置
     *
     * @param id id
     * @return 报警配置
     */
    Alarm findAlarmById(Integer id);

    /**
     * 根据id查询报警用户组
     *
     * @param id id
     * @return 报警用户组
     */
    AlarmGroup findAlarmGroupById(Integer id);

    /**
     * 根据报警配置用户组id，查询用户信息，只查用户状态为0（status:0）的用户
     *
     * @param alarmGroupId 报警配置用户组id
     * @return 用户信息集合
     */
    List<User> findUserByAlarmGroupId(Integer alarmGroupId);

    /**
     * 更新缓存
     *
     * @param notifyAlarm 更改配置通知内容
     */
    void updateConfig(NotifyAlarm notifyAlarm);

}

package com.bazzi.common.generic;

import lombok.Data;

import java.util.List;

@Data
public class NotifyAlarm {
    private int id;
    private int type;

    private List<Integer> idList;

    private NotifyAlarm() {
    }

    private NotifyAlarm(int id, int type) {
        this.id = id;
        this.type = type;
    }

    private NotifyAlarm(List<Integer> idList, int type) {
        this.idList = idList;
        this.type = type;
    }

    /**
     * 项目表增删改，通知报警模块
     *
     * @param id 项目id
     * @return NotifyAlarm
     */
    public static NotifyAlarm ofProject(int id) {
        return new NotifyAlarm(id, 1);
    }

    /**
     * 报警表增删改，通知报警模块
     *
     * @param id 报警id
     * @return NotifyAlarm
     */
    public static NotifyAlarm ofAlarm(int id) {
        return new NotifyAlarm(id, 2);
    }

    /**
     * 报警用户组表增删改，通知报警模块
     *
     * @param id 报警用户组id
     * @return NotifyAlarm
     */
    public static NotifyAlarm ofAlarmGroup(int id) {
        return new NotifyAlarm(id, 3);
    }

    /**
     * 用户表更改时，通知报警模块包含更改该用户的所以用户组更新数据
     *
     * @param idList 用户组（id集合）
     * @return NotifyAlarm
     */
    public static NotifyAlarm ofAlarmGroup(List<Integer> idList) {
        return new NotifyAlarm(idList, 4);
    }

    /**
     * 监控表增删改，通知报警模块
     *
     * @param id 监控id
     * @return NotifyAlarm
     */
    public static NotifyAlarm ofMonitor(int id) {
        return new NotifyAlarm(id, 5);
    }

    public boolean isProject() {
        return this.type == 1;
    }

    public boolean isAlarm() {
        return this.type == 2;
    }

    public boolean isAlarmGroup() {
        return this.type == 3;
    }

    public boolean isAlarmGroupList() {
        return this.type == 4;
    }

    public boolean isMonitor() {
        return this.type == 5;
    }
}

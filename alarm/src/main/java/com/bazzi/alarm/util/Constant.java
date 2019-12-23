package com.bazzi.alarm.util;

public final class Constant {

    public static final String PROJECT_REDIS_KEY = "%s:project:%s";//项目key
    public static final String PROJECT_FIELD_REDIS_KEY = "%s:projectField:%s";//项目中属性信息key
    public static final String MONITOR_REDIS_KEY = "%s:monitor:%s";//监控策略key

    public static final String ALARM_REDIS_KEY = "%s:alarm:%s";//报警key
    public static final String ALARM_REDIS_GROUP_KEY = "%s:alarmGroup:%s";//报警用户组key
    public static final String USER_REDIS_KEY = "%s:userOfAlarm:%s";//报警用户key
}

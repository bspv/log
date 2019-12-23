package com.bazzi.common.util;

/**
 * 被不同模块共用的常量字符串
 */
public final class GenericConst {
    //redis key
    public static final String RTS_STATISTICAL_PREFIX = "RTS:Statistical:";
    public static final String RTS_STATISTICAL_KEY = RTS_STATISTICAL_PREFIX + "%s";//RTS:Statistical:{yyyy-MM-dd}
    public static final String RTS_STATISTICAL_PATTERN = RTS_STATISTICAL_PREFIX + "*";
    //总计统计key
    public static final String RTS_TOTAL_PREFIX = "RTS:total:";
    public static final String RTS_TOTAL_KEY = RTS_TOTAL_PREFIX + "%s:%s";//RTS:total:{yyyy-MM-dd}:{hh}
    public static final String RTS_TOTAL_PATTERN = RTS_TOTAL_PREFIX + "*";
    public static final String TOTAL_LOG_TRAFFIC = "totalLogTraffic";
    public static final String TOTAL_LOG_COUNT = "totalLogCount";
    public static final String TOTAL_MONITOR = "totalMonitorNum";
    public static final String TOTAL_ALARM = "totalAlarmNum";

    //基于项目的统计key
    public static final String RTS_PROJECT_PREFIX = "RTS:project:";
    public static final String RTS_PROJECT_KEY = RTS_PROJECT_PREFIX + "%s:%s:%s";//RTS:project:{yyyy-MM-dd}:{hh}:{projectId}
    public static final String RTS_PROJECT_PATTERN = RTS_PROJECT_PREFIX + "*";
    public static final String LOG_TRAFFIC = "logTraffic";
    public static final String LOG_COUNT = "logCount";
    public static final String MONITOR = "monitorNum";
    public static final String ALARM = "alarmNum";

    //统计每个项目策略命中、报警的key
    public static final String RTS_DETAIL_PREFIX = "RTS:detail:";
    public static final String RTS_DETAIL_KEY = RTS_DETAIL_PREFIX + "%s:%s:%s";//RTS:detail:{yyyy-MM-dd}:{hh}:{projectId}
    public static final String RTS_DETAIL_PATTERN = RTS_DETAIL_PREFIX + "*";
    public static final String DETAIL_MONITOR = "%s_%s_monitor";//{monitorId}_{alarmId}_monitor
    public static final String DETAIL_MONITOR_SUFFIX = "_monitor";//_monitor
    public static final String DETAIL_ALARM = "%s_%s_alarm";//{monitorId}_{alarmId}_alarm


    //topic
    public static final String TOPIC_ALARM = "topic_log_alarm";//日志报警消息的topic
    public static final String TOPIC_NOTIFY = "topic_log_notify";//管理后台通知报警模块的topic

    //zookeeper
    public static final String ZK_DATA_PATH = "/analysisData";//zookeeper中数据存储路径
}

package com.bazzi.analysis.util;

import com.bazzi.analysis.bean.StrategyConfig;

public final class Constant {

    public static final String DETAIL_DELIMITER = "_";//统计明细的分隔符
    public static final String DETAIL_FORMAT = "%s" + DETAIL_DELIMITER + "%s";//monitorId_alarmId

    public static final long SYNC_INTERVAL_MILLIS = 10 * 1000;//系统级的数据同步间隔，毫秒
    public static final long SYNC_PROJECT_INTERVAL_MILLIS = 60 * 1000;//项目级的数据同步间隔，毫秒

    private static final String MERGE_KEY = "merge:%s:{%s}";//合并标识key
    private static final String HAPPEN_KEY = "happen:%s:{%s}";//触发队列的key
    private static final String BUFFER_KEY = "buffer:%s:%s";//缓冲队列的key

    public static final int BUFFER_QUEUE_SECOND = 3600;//缓冲队列在redis中的过期时间，秒

    public static final int BUFFER_QUEUE_RANDOM_LEN = 5;//缓冲队列，在时间戳后追加的随机数长度

    /**
     * 获取合并标识key
     *
     * @param sc 策略配置
     * @return 合并标识key
     */
    public static String getMergeKey(StrategyConfig sc) {
        return String.format(MERGE_KEY, sc.getProjectId(), sc.getMonitorId());
    }

    /**
     * 获取命中策略队列的key
     *
     * @param sc 策略配置
     * @return 命中策略队列的key
     */
    public static String getHappenKey(StrategyConfig sc) {
        return String.format(HAPPEN_KEY, sc.getProjectId(), sc.getMonitorId());
    }

    /**
     * 获取缓冲队列的key
     *
     * @param sc 策略配置
     * @return 缓冲队列的key
     */
    public static String getBufferKey(StrategyConfig sc) {
        return String.format(BUFFER_KEY, sc.getProjectId(), sc.getMonitorId());
    }

}

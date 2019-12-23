package com.bazzi.analysis.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

public final class StatisticalData {
    public static volatile LongAdder totalLogTraffic = new LongAdder();//总字节数
    public static volatile LongAdder totalLogCount = new LongAdder();//总记录数
    public static volatile LongAdder totalMonitor = new LongAdder();//总策略命中数
    public static volatile LongAdder totalAlarm = new LongAdder();//总报警数

    // 记录各系统日志数量、流量、报警次数、监控次数 ConcurrentHashMap<logFileName, LongAdder>
    public static volatile ConcurrentHashMap<String, LongAdder> projectLogCountMap = new ConcurrentHashMap<>();
    public static volatile ConcurrentHashMap<String, LongAdder> projectLogTrafficMap = new ConcurrentHashMap<>();
    public static volatile ConcurrentHashMap<String, LongAdder> projectMonitorMap = new ConcurrentHashMap<>();
    public static volatile ConcurrentHashMap<String, LongAdder> projectAlarmMap = new ConcurrentHashMap<>();

    // 记录各系统明细报警、监控次数 ConcurrentHashMap<logFileName, ConcurrentHashMap<monitorId+"_"+alarmId, LongAdder>>
    public static volatile ConcurrentHashMap<String, ConcurrentHashMap<String, LongAdder>> projectDetailMonitorMap = new ConcurrentHashMap<>();
    public static volatile ConcurrentHashMap<String, ConcurrentHashMap<String, LongAdder>> projectDetailAlarmMap = new ConcurrentHashMap<>();

}

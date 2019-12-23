package com.bazzi.analysis.bean;

import lombok.Data;

import java.util.regex.Pattern;

@Data
public class StrategyConfig {
    private String logFileName;//日志文件名

    private Integer monitorId;//monitorId

    private String monitorName;//策略名称

    private Integer projectId;//项目ID

    private String projectName;//项目名称

    private Integer alarmId;//报警ID

    private Integer captureType;//捕获类型，0不解析捕获正则，1解析捕获正则

    private String captureRegular;//捕获正则

    private String monitorRegular;//监控正则

    private String contentTemplate;//发送内容模板

    private Integer useTmp;//使用模板，0不使用，1使用

    private Integer handleType;//处理类型，0实时，1延迟

    private Integer calTime;//统计时间，单位秒

    private Integer calNum;//单位时间内次数

    private Integer sendInterval;//发送间隔，单位秒

    private Integer priority;//监控优先级（1-9）

    private Pattern pattern;

    private static final String ALARM_TEMPLATE = "【%s】系统%s产生%s条策略为【%s】的报警，请尽快关注!";
    private static final String ALARM_MERGE_TEMPLATE = "【%s】至【%s】系统%s产生%s条策略为【%s】的报警，请尽快关注!";

    public void initConfig() {
        this.pattern = monitorRegular != null ? Pattern.compile(monitorRegular) : null;
    }

    public String getContent(String triggerTime, long count, String currentTime) {
        if (useTmp == 1)
            return contentTemplate;
        if (count > 1)
            return String.format(ALARM_MERGE_TEMPLATE, triggerTime, currentTime, projectName, String.valueOf(count), monitorName);
        else
            return String.format(ALARM_TEMPLATE, triggerTime, projectName, String.valueOf(count), monitorName);
    }

    public boolean configMatcher(String msg) {
        boolean isFind = false;
        if (pattern != null && msg != null && !"".equals(msg)) {
            isFind = pattern.matcher(msg).find();
        }
        return isFind;
    }

}

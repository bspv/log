package com.bazzi.common.generic;

import lombok.Data;

@Data
public class AlarmDesc {

    private Integer monitorId;//策略Id

    private String monitorName;//策略名称

    private Integer projectId;//项目ID

    private String projectName;//项目名称

    private Integer alarmId;//报警ID

    private String content;//发送内容

    //日志信息中获取
    private String ip;//消息产生的机器IP
    private String platform;//平台，windows还是其他的
    private String message;//消息日志详情
    private String triggerTime;//日志时间

    //统计报警
    private String firstTrigger;//第一次触发时间，单条报警即日志时间，合并则为最早一条日志时间
    private long count;//次数，单条为1，合并则为具体数值

}

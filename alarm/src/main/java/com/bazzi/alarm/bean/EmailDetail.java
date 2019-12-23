package com.bazzi.alarm.bean;

import lombok.Data;

import java.util.Map;

@Data
public class EmailDetail {
    private String projectName;//项目名称

    private String triggerTime;//日志时间

    private String monitorName;//策略名称

    private String ip;//消息产生的机器IP

    private String platform;//平台，windows还是其他的

    private String content;//描述

    private String message;//消息日志详情

    private Integer captureType = 0;//捕获类型，0不解析捕获正则，1解析捕获正则

    private Map<String, String> msgMap;//解析的各字段

}

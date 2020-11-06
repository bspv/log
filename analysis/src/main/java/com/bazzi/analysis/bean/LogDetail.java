package com.bazzi.analysis.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class LogDetail {
    @JsonProperty(value = "@timestamp")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date timestamp;

    @JsonProperty(value = "@metadata")
    private Metadata metadata;

    @JsonProperty(value = "host")
    private Host host;

    @JsonProperty(value = "agent")
    private Agent agent;

    @JsonProperty(value = "ecs")
    private Ecs ecs;

    @JsonProperty(value = "log")
    private Log log;

    @JsonProperty(value = "message")
    private String message;

    @JsonProperty(value = "input")
    private Input input;

    @JsonProperty(value = "serverIp")
    private String serverIp;

    @JsonProperty(value = "fileName")
    private String fileName;

    private int len;
}

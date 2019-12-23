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

    @JsonProperty(value = "input")
    private Input input;

    @JsonProperty(value = "prospector")
    private Prospector prospector;

    @JsonProperty(value = "beat")
    private Beat beat;

    @JsonProperty(value = "host")
    private Host host;

    @JsonProperty(value = "source")
    private String source;

    @JsonProperty(value = "offset")
    private Long offset;

    @JsonProperty(value = "message")
    private String message;

    @JsonProperty(value = "serverIp")
    private String serverIp;

    private int len;
}

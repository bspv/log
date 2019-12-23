package com.bazzi.analysis.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Metadata {

    @JsonProperty(value = "beat")
    private String beat;

    @JsonProperty(value = "type")
    private String type;

    @JsonProperty(value = "version")
    private String version;

    @JsonProperty(value = "topic")
    private String topic;
}

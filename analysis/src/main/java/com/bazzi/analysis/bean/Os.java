package com.bazzi.analysis.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Os {

    @JsonProperty(value = "version")
    private String version;

    @JsonProperty(value = "family")
    private String family;

    @JsonProperty(value = "build")
    private String build;

    @JsonProperty(value = "platform")
    private String platform;

}

package com.bazzi.analysis.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Beat {

    @JsonProperty(value = "version")
    private String version;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "hostname")
    private String hostname;

}

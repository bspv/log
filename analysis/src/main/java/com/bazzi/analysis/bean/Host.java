package com.bazzi.analysis.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Host {
    @JsonProperty(value = "architecture")
    private String architecture;

    @JsonProperty(value = "os")
    private Os os;

    @JsonProperty(value = "id")
    private String id;

    @JsonProperty(value = "ip")
    private List<String> ip;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "mac")
    private List<String> mac;

    @JsonProperty(value = "hostname")
    private String hostname;

}

package com.bazzi.analysis.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Agent {
    @JsonProperty(value = "ephemeral_id")
    private String ephemeralId;

    @JsonProperty(value = "id")
    private String id;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "type")
    private String type;

    @JsonProperty(value = "version")
    private String version;

    @JsonProperty(value = "hostname")
    private String hostname;
}

package com.bazzi.analysis.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Input {
    @JsonProperty(value = "type")
    private String type;
}

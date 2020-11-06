package com.bazzi.analysis.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FileInfo {
    @JsonProperty(value = "path")
    private String path;
}

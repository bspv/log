package com.bazzi.analysis.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Log {
    @JsonProperty(value = "offset")
    private int offset;

    @JsonProperty(value = "file")
    private FileInfo fileInfo;
}

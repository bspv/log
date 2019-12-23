package com.bazzi.common.generic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotifyAnalysis implements Serializable {
    private static final long serialVersionUID = -7755012166596655499L;

    private int projectId;//项目ID
    private String logFileName;//日志文件名，当删除Project时，此字段必填

    public NotifyAnalysis(int projectId) {
        this.projectId = projectId;
    }
}

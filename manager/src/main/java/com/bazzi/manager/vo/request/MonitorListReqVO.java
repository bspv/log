package com.bazzi.manager.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonitorListReqVO extends PageReqVO {
    @ApiModelProperty(value = "监控策略名")
    private String monitorName;

    @ApiModelProperty(value = "项目ID")
    private Integer projectId;

    @ApiModelProperty(value = "报警ID")
    private Integer alarmId;
}

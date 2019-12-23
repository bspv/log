package com.bazzi.manager.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProjectTotalVO implements Serializable {
    private static final long serialVersionUID = -6545811766800498552L;

    @ApiModelProperty(value = "项目ID")
    private Integer projectId;

    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "日志记录行数")
    private Long logCount;

    @ApiModelProperty(value = "日志字节数，单位KB")
    private Double logTraffic;

    @ApiModelProperty(value = "监控命中数")
    private Long monitorNum;

    @ApiModelProperty(value = "报警数")
    private Long alarmNum;

    @ApiModelProperty(value = "统计时间，yyyy-MM-dd HH格式")
    private String totalTime;
}

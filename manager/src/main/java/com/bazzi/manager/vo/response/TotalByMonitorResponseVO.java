package com.bazzi.manager.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TotalByMonitorResponseVO implements Serializable {
    private static final long serialVersionUID = 2252732438523914770L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "项目ID")
    private Integer projectId;

    @ApiModelProperty(value = "项目名")
    private String projectName;

    @ApiModelProperty(value = "项目中文名")
    private String projectCnName;

    @ApiModelProperty(value = "日志文件名")
    private String logFileName;

    @ApiModelProperty(value = "监控策略ID")
    private Integer monitorId;

    @ApiModelProperty(value = "监控策略名")
    private String monitorName;

    @ApiModelProperty(value = "报警ID")
    private Integer alarmId;

    @ApiModelProperty(value = "报警名")
    private String alarmName;

    @ApiModelProperty(value = "监控命中数")
    private Long monitorNum;

    @ApiModelProperty(value = "报警数")
    private Long alarmNum;

    @ApiModelProperty(value = "统计日期，yyyy-MM-dd")
    private String totalDay;

    @ApiModelProperty(value = "统计小时，HH")
    private String totalHour;

    @ApiModelProperty(value = "记录生成时间")
    private Date createTime;
}

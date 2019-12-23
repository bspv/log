package com.bazzi.manager.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TotalByProjectResponseVO implements Serializable {
    private static final long serialVersionUID = -3462507247532244777L;

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

    @ApiModelProperty(value = "日志记录数")
    private Long logCount;

    @ApiModelProperty(value = "日志字节数，单位KB")
    private Double logTraffic;

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

package com.bazzi.manager.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TotalByHourResponseVO implements Serializable {
    private static final long serialVersionUID = 2783631611006076540L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "日志记录数")
    private Long totalLogCount;

    @ApiModelProperty(value = "日志字节数，单位KB")
    private Double totalLogTraffic;

    @ApiModelProperty(value = "监控命中数")
    private Long totalMonitorNum;

    @ApiModelProperty(value = "报警数")
    private Long totalAlarmNum;

    @ApiModelProperty(value = "统计日期，yyyy-MM-dd")
    private String totalDay;

    @ApiModelProperty(value = "统计小时，HH")
    private String totalHour;

    @ApiModelProperty(value = "记录生成时间")
    private Date createTime;
}

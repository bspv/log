package com.bazzi.manager.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class LogDetailHourVO implements Serializable {
    private static final long serialVersionUID = -1174663184765554611L;

    @ApiModelProperty(value = "日志记录行数")
    private Long totalLogCount;

    @ApiModelProperty(value = "日志字节数，单位KB")
    private Double totalLogTraffic;

    @ApiModelProperty(value = "监控命中数")
    private Long totalMonitorNum;

    @ApiModelProperty(value = "报警数")
    private Long totalAlarmNum;

    @ApiModelProperty(value = "小时，00-23")
    private String hour;
}

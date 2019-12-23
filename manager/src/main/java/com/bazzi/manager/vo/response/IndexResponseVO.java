package com.bazzi.manager.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class IndexResponseVO implements Serializable {
    private static final long serialVersionUID = -7550769500146988704L;

    @ApiModelProperty(value = "日志记录行数")
    private Long totalLogCount;

    @ApiModelProperty(value = "日志字节数，单位KB")
    private Double totalLogTraffic;

    @ApiModelProperty(value = "监控命中数")
    private Long totalMonitorNum;

    @ApiModelProperty(value = "报警数")
    private Long totalAlarmNum;

    @ApiModelProperty(value = "年月日")
    private String ymd;

    @ApiModelProperty(value = "小时")
    private String hour;

    @ApiModelProperty(value = "当天的日志记录、日志字节数、监控命中数、报警数集合")
    private List<LogDetailHourVO> detailHourList;

    @ApiModelProperty(value = "最近一小时，报警排名前10(最多10条)的项目信息")
    private List<ProjectTotalVO> projectTotalList;
}

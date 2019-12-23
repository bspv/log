package com.bazzi.manager.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TotalByProjectReqVO extends PageReqVO {

    @ApiModelProperty(value = "项目名")
    private String projectName;

    @ApiModelProperty(value = "项目中文名")
    private String projectCnName;

    @ApiModelProperty(value = "日志文件名")
    private String logFileName;

    @ApiModelProperty(value = "开始的统计时间，yyyy-MM-dd HH:00:00格式")
    private String totalTimeStart;

    @ApiModelProperty(value = "结束的统计时间，yyyy-MM-dd HH:00:00格式")
    private String totalTimeEnd;
}

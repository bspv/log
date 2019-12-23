package com.bazzi.manager.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TotalByHourReqVO extends PageReqVO {

    @ApiModelProperty(value = "开始的统计时间，yyyy-MM-dd HH:00:00格式")
    private String totalTimeStart;

    @ApiModelProperty(value = "结束的统计时间，yyyy-MM-dd HH:00:00格式")
    private String totalTimeEnd;
}

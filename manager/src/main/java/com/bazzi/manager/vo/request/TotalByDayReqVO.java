package com.bazzi.manager.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TotalByDayReqVO extends PageReqVO {

    @ApiModelProperty(value = "开始的统计日期，只有年月日，yyyy-MM-dd格式")
    private String totalDayStart;

    @ApiModelProperty(value = "结束的统计日期，只有年月日，yyyy-MM-dd格式")
    private String totalDayEnd;
}

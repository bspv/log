package com.bazzi.manager.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName AlarmListReqVO
 * @Author baoyf
 * @Date 2019/1/24 11:24
 **/
@Getter
@Setter
public class AlarmListReqVO extends PageReqVO {

    @ApiModelProperty(value = "报警名称")
    private String alarmName;

    @ApiModelProperty(value = "状态")
    private Integer status;

}

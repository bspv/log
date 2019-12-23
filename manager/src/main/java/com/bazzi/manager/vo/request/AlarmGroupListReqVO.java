package com.bazzi.manager.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName AlarmGroupListReqVO
 * @Author baoyf
 * @Date 2019/1/23 16:53
 **/
@Getter
@Setter
public class AlarmGroupListReqVO extends PageReqVO {

    @ApiModelProperty(value = "组名")
    private String groupName;

    @ApiModelProperty(value = "组状态，0正常，1禁用")
    private Integer status;

}

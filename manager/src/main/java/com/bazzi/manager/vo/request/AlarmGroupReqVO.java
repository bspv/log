package com.bazzi.manager.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @ClassName AlarmGroupReqVO
 * @Author baoyf
 * @Date 2019/1/23 17:25
 **/
@Data
public class AlarmGroupReqVO {

    @NotEmpty(message = "组名不能为空")
    @ApiModelProperty(value = "组名")
    private String groupName;

    @ApiModelProperty(value = "组描述")
    private String groupDesc;

    @NotNull(message = "组状态不能为空")
    @ApiModelProperty(value = "组状态，0正常，1禁用")
    private Integer status;

}

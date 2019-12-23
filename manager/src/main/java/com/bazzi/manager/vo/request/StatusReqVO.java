package com.bazzi.manager.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @ClassName StatusReqVO
 * @Author baoyf
 * @Date 2019/1/24 16:37
 **/
@Data
public class StatusReqVO {

    @NotNull(message = "ID不能为空")
    @ApiModelProperty(value = "主键ID")
    private Integer id;

    @NotNull(message = "状态不能为空")
    @ApiModelProperty(value = "状态")
    private Integer status;

}

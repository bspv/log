package com.bazzi.manager.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class MenuUpdateReqVO {

    @NotNull(message = "菜单ID不能为空")
    @ApiModelProperty(value = "菜单ID", required = true)
    private Integer id;

    @NotEmpty(message = "菜单名称不能为空")
    @ApiModelProperty(value = "菜单名称", required = true)
    private String menuName;

    @NotEmpty(message = "菜单url不能为空")
    @ApiModelProperty(value = "菜单url", required = true)
    private String menuUrl;

    @ApiModelProperty(value = "页面地址")
    private String pageUrl;

    @ApiModelProperty(value = "图标名称")
    private String icon;

    @NotNull(message = "权重不能为空")
    @ApiModelProperty(value = "权重，数值越大越靠前", required = true)
    private Integer weight = 0;
}

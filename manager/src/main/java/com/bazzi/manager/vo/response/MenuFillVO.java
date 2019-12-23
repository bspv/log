package com.bazzi.manager.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class MenuFillVO implements Serializable {
    private static final long serialVersionUID = 9053569299323714448L;

    @ApiModelProperty(value = "菜单ID")
    private Integer id;

    @ApiModelProperty(value = "菜单类型，0目录，1菜单")
    private Integer menuType;
}

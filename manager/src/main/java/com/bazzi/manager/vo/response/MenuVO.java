package com.bazzi.manager.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MenuVO implements Serializable {
    private static final long serialVersionUID = 6691356790255021656L;

    @ApiModelProperty(value = "菜单ID")
    private Integer id;

    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    @ApiModelProperty(value = "菜单url")
    private String menuUrl;

    @ApiModelProperty(value = "菜单类型，0目录，1菜单")
    private Integer menuType;

    @ApiModelProperty(value = "页面地址")
    private String pageUrl;

    @ApiModelProperty(value = "图标名称")
    private String icon;

    @ApiModelProperty(value = "父菜单ID")
    private Integer pid;

    @ApiModelProperty(value = "权重，数值越大越靠前")
    private Integer weight;

    @ApiModelProperty(value = "子菜单集合")
    private List<MenuVO> childMenu;
}

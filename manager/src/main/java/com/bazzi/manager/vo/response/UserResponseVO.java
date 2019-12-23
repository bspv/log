package com.bazzi.manager.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserResponseVO implements Serializable {
    private static final long serialVersionUID = -1352645214822072174L;

    @ApiModelProperty(value = "信息")
    private Integer id;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "头像地址")
    private String avatarUrl;

    @ApiModelProperty(value = "员工编号")
    private String employeeCode;

    @ApiModelProperty(value = "菜单集合")
    private List<MenuVO> menuList;

    @ApiModelProperty(value = "动态token")
    private String dynamicToken;

}

package com.bazzi.manager.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserSimpleVO implements Serializable {
    private static final long serialVersionUID = -2168604099291491289L;

    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "用户状态，0正常，1禁用")
    private Integer status;

}

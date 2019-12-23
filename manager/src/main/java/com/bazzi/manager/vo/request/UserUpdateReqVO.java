package com.bazzi.manager.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class UserUpdateReqVO {

    @NotNull(message = "用户ID不能为空")
    @ApiModelProperty(value = "用户ID", required = true)
    private Integer id;

    @ApiModelProperty(value = "密码，传原值(此处不要md5)，密码不要回填编辑框")
    private String password;

    @NotEmpty(message = "手机号不能为空")
    @Pattern(regexp = "^1\\d{10}", message = "手机号必须为11位数字")
    @ApiModelProperty(value = "手机号", required = true)
    private String phone;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "头像url")
    private String avatarUrl;

    @NotEmpty(message = "邮箱不能为空")
    @Pattern(regexp = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}", message = "请输入正确的邮箱")
    @ApiModelProperty(value = "邮箱地址", required = true)
    private String email;

    @ApiModelProperty(value = "QQ号")
    private String qq;

    @ApiModelProperty(value = "微信号")
    private String weChat;

    @NotEmpty(message = "员工编号不能为空")
    @ApiModelProperty(value = "员工编号", required = true)
    private String employeeCode;

    @ApiModelProperty(value = "员工部门")
    private String department;

    @ApiModelProperty(value = "用户状态(0:有效；1：无效)")
    private Integer status = 0;
}

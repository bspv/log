package com.bazzi.manager.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class LoginReqVO {
    @NotEmpty(message = "用户名不能为空")
    @Pattern(regexp = "\\w+", message = "用户名只能是字母+数字")
    @ApiModelProperty(value = "用户名", required = true)
    private String userName;

    @NotEmpty(message = "密码不能为空")
    @ApiModelProperty(value = "密码，MD5(大写)", required = true)
    private String password;
}

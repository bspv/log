package com.bazzi.manager.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName GroupUserResponseVO
 * @Author baoyf
 * @Date 2019/1/23 19:36
 **/
@Data
public class GroupUserResponseVO implements Serializable {
    private static final long serialVersionUID = -2648877578855114086L;

    @ApiModelProperty(value = "用户ID")
    private Integer id;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "姓名")
    private String realName;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

}

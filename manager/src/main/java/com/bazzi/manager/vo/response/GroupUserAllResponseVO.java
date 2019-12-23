package com.bazzi.manager.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName GroupUserAllResponseVO
 * @Author baoyf
 * @Date 2019/1/24 15:01
 **/
@Data
public class GroupUserAllResponseVO implements Serializable {
    private static final long serialVersionUID = 2956719817824560765L;

    @ApiModelProperty(value = "用户组ID")
    private Integer id;

    @ApiModelProperty(value = "用户组名称")
    private String groupName;

    @ApiModelProperty(value = "用户组状态")
    private Integer status;

}

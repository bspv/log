package com.bazzi.manager.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UserMenuReqVO {
    @NotNull(message = "用户ID不能为空")
    @ApiModelProperty(value = "用户ID", required = true)
    private Integer userId;

    @NotNull(message = "菜单ID集合不能为空")
    @ApiModelProperty(value = "菜单ID集合", required = true)
    private List<Integer> menuIds;
}

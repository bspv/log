package com.bazzi.manager.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @ClassName GroupUserReqVO
 * @Author baoyf
 * @Date 2019/1/23 19:51
 **/
@Data
public class GroupUserReqVO {

    @ApiModelProperty(value = "用户组ID")
    @NotNull(message = "用户组ID不能为空")
    private Integer alarmGroupId;

    @ApiModelProperty(value = "用户数组")
    @NotNull(message = "用户数组不能为空")
    private List<Integer> userList;

}

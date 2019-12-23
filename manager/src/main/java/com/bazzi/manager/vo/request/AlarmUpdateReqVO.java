package com.bazzi.manager.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @ClassName AlarmUpdateReqVO
 * @Author baoyf
 * @Date 2019/1/24 15:53
 **/
@Data
public class AlarmUpdateReqVO {

    @NotNull(message = "报警配置ID不能为空")
    @ApiModelProperty(value = "报警配置ID")
    private Integer id;

    @NotEmpty(message = "报警名称不能为空")
    @ApiModelProperty(value = "报警名称")
    private String alarmName;

    @NotEmpty(message = "报警方式不能为空")
    @ApiModelProperty(value = "报警方式")
    private List<String> alarmMode;

    @NotNull(message = "用户组ID")
    @ApiModelProperty(value = "用户组ID")
    private Integer alarmGroupId;

    @ApiModelProperty(value = "报警描述")
    private String alarmDesc;

    @NotNull(message = "状态不能为空")
    @ApiModelProperty(value = "状态")
    private Integer status;

}

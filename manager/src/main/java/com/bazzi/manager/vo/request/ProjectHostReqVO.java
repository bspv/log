package com.bazzi.manager.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ProjectHostReqVO {
    @NotEmpty(message = "主机名不能为空")
    @ApiModelProperty(value = "主机名", required = true)
    private String hostName;

    @NotEmpty(message = "ip地址不能为空")
    @ApiModelProperty(value = "ip地址", required = true)
    private String ip;

    @NotNull(message = "系统类型不能为空")
    @ApiModelProperty(value = "系统类型，0Linux，1Windows", required = true)
    private Integer osType;

    @ApiModelProperty(value = "备注")
    private String detail;
}

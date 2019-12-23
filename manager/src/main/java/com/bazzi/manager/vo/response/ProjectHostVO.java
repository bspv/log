package com.bazzi.manager.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProjectHostVO implements Serializable {
    private static final long serialVersionUID = -8198245256220012055L;

    @ApiModelProperty(value = "主机名")
    private String hostName;

    @ApiModelProperty(value = "ip地址")
    private String ip;

    @ApiModelProperty(value = "系统类型，0Linux，1Windows")
    private Integer osType;

    @ApiModelProperty(value = "备注")
    private String detail;
}

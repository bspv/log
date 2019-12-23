package com.bazzi.manager.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProjectSimpleVO implements Serializable {
    private static final long serialVersionUID = -1978574535098430089L;

    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "项目中文名")
    private String projectCnName;
}

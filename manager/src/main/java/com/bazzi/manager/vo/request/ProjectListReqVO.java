package com.bazzi.manager.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectListReqVO extends PageReqVO {
    @ApiModelProperty(value = "项目名")
    private String projectName;

}

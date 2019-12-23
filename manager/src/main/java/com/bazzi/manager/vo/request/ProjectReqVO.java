package com.bazzi.manager.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ProjectReqVO {
    @NotEmpty(message = "项目名不能为空")
    @ApiModelProperty(value = "项目名", required = true)
    private String projectName;

    @ApiModelProperty(value = "项目中文名")
    private String projectCnName;

    @NotEmpty(message = "日志文件名不能为空")
    @ApiModelProperty(value = "日志文件名", required = true)
    private String logFileName;

    @NotNull(message = "捕获类型不能为空")
    @ApiModelProperty(value = "捕获类型，0不解析捕获正则，1解析捕获正则，当传1时，logRegular和delimiter必填", required = true)
    private Integer captureType;

    @ApiModelProperty(value = "日志配置文件里的正则")
    private String logRegular;

    @ApiModelProperty(value = "分隔符")
    private String delimiter;

    @ApiModelProperty(value = "项目描述")
    private String projectDesc;

    @ApiModelProperty(value = "项目状态(0:有效；1：无效)")
    private Integer status;

    @NotNull(message = "Host不能为空")
    @ApiModelProperty(value = "Host集合数据", required = true)
    private List<ProjectHostReqVO> hostList;
}

package com.bazzi.manager.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ProjectVO implements Serializable {
    private static final long serialVersionUID = 7036255762559475226L;

    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "项目中文名")
    private String projectCnName;

    @ApiModelProperty(value = "日志文件名")
    private String logFileName;

    @ApiModelProperty(value = "捕获类型，0不解析捕获正则，1解析捕获正则")
    private Integer captureType;

    @ApiModelProperty(value = "日志文件正则")
    private String logRegular;

    @ApiModelProperty(value = "分隔符")
    private String delimiter;

    @ApiModelProperty(value = "捕获正则")
    private String captureRegular;

    @ApiModelProperty(value = "项目描述")
    private String projectDesc;

    @ApiModelProperty(value = "项目状态(0:有效；1：无效)")
    private Integer status;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "最近一次更新人")
    private String updateUser;

    @ApiModelProperty(value = "最近一次更新时间")
    private Date updateTime;
}

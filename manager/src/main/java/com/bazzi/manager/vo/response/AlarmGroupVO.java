package com.bazzi.manager.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName AlarmGroupVO
 * @Author baoyf
 * @Date 2019/1/23 16:42
 **/
@Data
public class AlarmGroupVO implements Serializable {
    private static final long serialVersionUID = 4317538318720021944L;

    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "组名")
    private String groupName;

    @ApiModelProperty(value = "组描述")
    private String groupDesc;

    @ApiModelProperty(value = "组状态，0正常，1禁用")
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

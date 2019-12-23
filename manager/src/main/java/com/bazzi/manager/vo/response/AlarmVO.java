package com.bazzi.manager.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName AlarmVO
 * @Author baoyf
 * @Date 2019/1/24 11:26
 **/
@Data
public class AlarmVO implements Serializable {
    private static final long serialVersionUID = 7983214413792943264L;

    @ApiModelProperty(value = "报警ID")
    private Integer id;

    @ApiModelProperty(value = "报警名称")
    private String alarmName;

    @ApiModelProperty(value = "报警方式，0邮件，1短信，2微信")
    private String alarmMode;

    @ApiModelProperty(value = "对应用户组ID")
    private Integer alarmGroupId;

    @ApiModelProperty(value = "报警描述")
    private String alarmDesc;

    @ApiModelProperty(value = "状态，0正常，1禁用")
    private Integer status;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

}

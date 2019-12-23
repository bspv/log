package com.bazzi.manager.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AlarmRecordVO implements Serializable {
    private static final long serialVersionUID = 1261915637623903386L;

    @ApiModelProperty(value = "报警记录id")
    private Long id;

    @ApiModelProperty(value = "项目名")
    private String projectName;

    @ApiModelProperty(value = "日志文件名")
    private String logFileName;

    @ApiModelProperty(value = "监控策略名")
    private String monitorName;

    @ApiModelProperty(value = "报警名称")
    private String alarmName;

    @ApiModelProperty(value = "报警方式，0邮件，1短信，2微信，如果是邮件+短信，则0#1，如果是邮件+短信+微信，则0#1#2，其他以此类推")
    private String alarmMode;

    @ApiModelProperty(value = "用户组名")
    private String groupName;

    @ApiModelProperty(value = "用户组下用户数量(状态正常的)")
    private Integer userNum;

    @ApiModelProperty(value = "报警机器IP")
    private String ip;

    @ApiModelProperty(value = "首次触发时间")
    private Date firstTrigger;

    @ApiModelProperty(value = "当前触发时间")
    private Date triggerTime;

    @ApiModelProperty(value = "触发次数")
    private Integer num;

    @ApiModelProperty(value = "日志产生的平台")
    private String platform;

    @ApiModelProperty(value = "状态，0报警成功，1报警失败")
    private Integer status;

    @ApiModelProperty(value = "错误代码")
    private String errCode;

    @ApiModelProperty(value = "报警记录生成时间")
    private Date createTime;
}

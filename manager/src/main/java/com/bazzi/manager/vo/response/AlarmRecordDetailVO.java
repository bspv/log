package com.bazzi.manager.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AlarmRecordDetailVO implements Serializable {
    private static final long serialVersionUID = -8730820913424094114L;

    @ApiModelProperty(value = "收件方，邮件则收件人邮箱，短信则存电话号码")
    private String recipient;

    @ApiModelProperty(value = "报警方式，0邮件，1短信，2微信")
    private Integer alarmMode;

    @ApiModelProperty(value = "报警发送状态，0成功，1失败")
    private Integer sendStatus;

    @ApiModelProperty(value = "发送失败原因")
    private String failMsg;

    @ApiModelProperty(value = "记录生成时间")
    private Date createTime;
}

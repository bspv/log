package com.bazzi.manager.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class AlarmRecordResponseVO implements Serializable {
    private static final long serialVersionUID = -6716323608222304096L;

    @ApiModelProperty(value = "报警记录id")
    private Long id;

    @ApiModelProperty(value = "项目id")
    private Integer projectId;

    @ApiModelProperty(value = "项目名")
    private String projectName;

    @ApiModelProperty(value = "日志文件名")
    private String logFileName;

    @ApiModelProperty(value = "捕获类型，0不解析捕获正则，1解析捕获正则")
    private Integer captureType;

    @ApiModelProperty(value = "项目状态(0：有效；1：无效)")
    private Integer projectStatus;

    @ApiModelProperty(value = "监控策略ID")
    private Integer monitorId;

    @ApiModelProperty(value = "监控策略名")
    private String monitorName;

    @ApiModelProperty(value = "策略正则")
    private String monitorRegular;

    @ApiModelProperty(value = "使用模板，0不使用，1使用")
    private Integer useTmp;

    @ApiModelProperty(value = "处理类型，0实时，1延迟")
    private Integer handleType;

    @ApiModelProperty(value = "统计时间，单位秒")
    private Integer calTime;

    @ApiModelProperty(value = "单位时间内次数")
    private Integer calNum;

    @ApiModelProperty(value = "发送间隔，单位秒")
    private Integer sendInterval;

    @ApiModelProperty(value = "监控优先级（1-9）")
    private Integer priority;

    @ApiModelProperty(value = "监控策略状态(0:有效；1：无效)")
    private Integer monitorStatus;

    @ApiModelProperty(value = "报警ID")
    private Integer alarmId;

    @ApiModelProperty(value = "报警名称")
    private String alarmName;

    @ApiModelProperty(value = "报警方式，0邮件，1短信，2微信，如果是邮件+短信，则0#1，如果是邮件+短信+微信，则0#1#2，其他以此类推")
    private String alarmMode;

    @ApiModelProperty(value = "报警状态(0：有效；1：无效)")
    private Integer alarmStatus;

    @ApiModelProperty(value = "报警用户组ID")
    private Integer alarmGroupId;

    @ApiModelProperty(value = "用户组名")
    private String groupName;

    @ApiModelProperty(value = "组状态(0：有效；1：无效)")
    private Integer groupStatus;

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

    @ApiModelProperty(value = "消息详情")
    private String message;

    @ApiModelProperty(value = "状态，0报警成功，1报警失败")
    private Integer status;

    @ApiModelProperty(value = "错误代码")
    private String errCode;

    @ApiModelProperty(value = "报警失败错误信息")
    private String errMsg;

    @ApiModelProperty(value = "报警记录生成时间")
    private Date createTime;

    @ApiModelProperty(value = "报警发送明细")
    private List<AlarmRecordDetailVO> list;
}

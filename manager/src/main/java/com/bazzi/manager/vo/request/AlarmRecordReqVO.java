package com.bazzi.manager.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlarmRecordReqVO extends PageReqVO {

    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "日志文件名")
    private String logFileName;

    @ApiModelProperty(value = "监控策略名")
    private String monitorName;

    @ApiModelProperty(value = "处理类型，0实时，1延迟")
    private Integer handleType;

    @ApiModelProperty(value = "优先级（1-9）")
    private Integer priority;

    @ApiModelProperty(value = "报警策略名")
    private String alarmName;

    @ApiModelProperty(value = "用户组名")
    private String groupName;

    @ApiModelProperty(value = "起始触发时间")
    private String triggerTimeStart;

    @ApiModelProperty(value = "结束触发时间")
    private String triggerTimeEnd;
}

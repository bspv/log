package com.bazzi.manager.vo.request;

import com.bazzi.common.annotation.GroupCfg;
import com.bazzi.common.annotation.ValidCfg;
import com.bazzi.manager.validator.GroupA;
import com.bazzi.manager.validator.GroupB;
import com.bazzi.manager.vo.MonitorTmpVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;
import java.util.List;

@Data
@ValidCfg(field = "strategyType",
        groupCfg = {
                @GroupCfg(val = "0", groups = {Default.class, GroupA.class}),
                @GroupCfg(val = "1", groups = {Default.class, GroupA.class}),
                @GroupCfg(val = "2", groups = {Default.class, GroupB.class})
        })
public class MonitorReqVO {
    @NotEmpty(message = "监控策略名不能为空")
    @ApiModelProperty(value = "监控策略名", required = true)
    private String monitorName;

    @NotNull(message = "项目ID不能为空")
    @ApiModelProperty(value = "项目ID", required = true)
    private Integer projectId;

    @NotNull(message = "报警ID不能为空")
    @ApiModelProperty(value = "报警ID", required = true)
    private Integer alarmId;

    @NotNull(message = "监控类型不能为空")
    @ApiModelProperty(value = "监控类型,，0异常监控", required = true)
    private Integer monitorType;

    @NotNull(message = "策略内容类型不能为空")
    @ApiModelProperty(value = "策略内容类型，0正则，1字符串，2字符串组合", required = true)
    private Integer strategyType;

    @NotEmpty(message = "监控策略内容不能为空", groups = {GroupA.class})
    @ApiModelProperty(value = "监控策略内容", required = true)
    private String monitorStrategy;

    @ApiModelProperty(value = "发送内容模板")
    private String contentTemplate;

    @NotNull(message = "是否使用模板不能为空")
    @ApiModelProperty(value = "是否使用模板，0不使用，1使用", required = true)
    private Integer useTmp;

    @NotNull(message = "处理类型不能为空")
    @ApiModelProperty(value = "处理类型，0实时，1延迟", required = true)
    private Integer handleType;

    @ApiModelProperty(value = "统计时间，单位秒")
    private Integer calTime;

    @ApiModelProperty(value = "单位时间内次数")
    private Integer calNum;

    @ApiModelProperty(value = "发送间隔，单位秒")
    private Integer sendInterval;

    @NotNull(message = "监控优先级不能为空")
    @ApiModelProperty(value = "监控优先级（1-9）", required = true)
    private Integer priority;

    @NotNull(message = "监控策略状态不能为空")
    @ApiModelProperty(value = "监控策略状态(0:有效；1：无效)", required = true)
    private Integer status;

    @Size(min = 1, max = 6, message = "字符串组合集合元素需在1到6个之间", groups = {GroupB.class})
    @ApiModelProperty(value = "字符串组合集合")
    private List<MonitorTmpVO> monitorTmpVOList;
}

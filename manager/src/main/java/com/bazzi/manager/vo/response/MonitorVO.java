package com.bazzi.manager.vo.response;

import com.bazzi.manager.vo.MonitorTmpVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class MonitorVO implements Serializable {
    private static final long serialVersionUID = 2844741371198729895L;

    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "监控策略名")
    private String monitorName;

    @ApiModelProperty(value = "项目ID")
    private Integer projectId;

    @ApiModelProperty(value = "报警ID")
    private Integer alarmId;

    @ApiModelProperty(value = "监控类型,，0异常监控")
    private Integer monitorType;

    @ApiModelProperty(value = "策略内容类型，0正则，1字符串，2字符串组合")
    private Integer strategyType;

    @ApiModelProperty(value = "监控策略内容")
    private String monitorStrategy;

    @ApiModelProperty(value = "发送内容模板")
    private String contentTemplate;

    @ApiModelProperty(value = "是否使用模板，0不使用，1使用")
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
    private Integer status;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "最近一次更新人")
    private String updateUser;

    @ApiModelProperty(value = "最近一次更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "字符串组合集合，当strategyType为2时，有相应的数据")
    private List<MonitorTmpVO> monitorTmpVOList;
}

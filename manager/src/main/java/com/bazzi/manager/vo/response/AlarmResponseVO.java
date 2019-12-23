package com.bazzi.manager.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName AlarmResponseVO
 * @Author baoyf
 * @Date 2019/1/29 9:30
 **/
@Data
public class AlarmResponseVO implements Serializable {
    private static final long serialVersionUID = -5260421500494643198L;

    @ApiModelProperty(value = "报警id")
    private Integer id;

    @ApiModelProperty(value = "报警名称")
    private String alarmName;

    @ApiModelProperty(value = "状态，0正常，1禁用")
    private Integer status;

}

package com.bazzi.manager.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdResponseVO implements Serializable {
    private static final long serialVersionUID = -603508475404220589L;

    @ApiModelProperty(value = "记录ID")
    private Integer id;
}

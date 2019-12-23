package com.bazzi.manager.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListVO<T> implements Serializable {
    private static final long serialVersionUID = -3234657914350055162L;

    @ApiModelProperty(value = "集合")
    private List<T> list;
}

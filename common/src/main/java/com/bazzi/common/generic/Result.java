package com.bazzi.common.generic;

import com.bazzi.common.ex.BusinessException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "Result")
public class Result<T extends Serializable> implements Serializable {
    private static final long serialVersionUID = -877799779908348979L;

    @ApiModelProperty(value = "泛型对象")
    private T data;// 数据

    @ApiModelProperty(value = "状态，true成功，false失败")
    private boolean status = true;// 状态

    @ApiModelProperty(value = "状态码")
    private String code = "";// 状态码

    @ApiModelProperty(value = "消息")
    private String message = "";// 消息

    public Result() {
    }

    public Result(T data) {
        super();
        this.data = data;
    }

    public void setError(String code, String message) {
        this.code = code;
        this.message = message;
        this.status = false;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 获取预期数据，status为true返回data，否则根据code和message扔出异常
     *
     * @return data里数据
     */
    @JsonIgnore
    public T getExpectedData() {
        if (!status)
            throw new BusinessException(code, message);
        return data;
    }
}

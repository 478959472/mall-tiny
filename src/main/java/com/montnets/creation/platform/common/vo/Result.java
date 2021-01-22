
package com.montnets.creation.platform.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Result<T> {

    @ApiModelProperty(value = "返回数据")
    private T data;

    @ApiModelProperty(value = "返回码")
    private int code;

    @ApiModelProperty(value = "返回码描述")
    private String msg;
//    @ApiModelProperty(value = "明细返回码")
//    private String subCode;
//    @ApiModelProperty(value = "明细返回码描述")
//    private String subMsg;

    public Result() {
    }

    public Result(T data, int code, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public static Result<Object> success(Object object, String message) {
        Result<Object> result = new Result<>();
        result.setData(object);
        result.setCode(200);
        result.setMsg(message);
        return result;
    }

    public static Result<Object> success(Object object) {
        Result<Object> result = new Result<>();
        result.setData(object);
        result.setCode(200);
        result.setMsg("");
        return result;
    }

    public static Result<Object> success(int code, Object object) {
        Result<Object> res = Result.success(object);
        res.setCode(code);
        return res;
    }

    public static Result<Object> fail(int code, String message) {
        Result<Object> result = new Result<>();
        result.setData(null);
        result.setCode(code);
        result.setMsg(message);
        return result;
    }



    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}


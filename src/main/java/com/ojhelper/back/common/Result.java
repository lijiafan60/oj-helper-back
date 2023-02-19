package com.ojhelper.back.common;

import lombok.Data;

import java.io.Serializable;


@Data
public class Result implements Serializable {
    private static final long serialVersionUID = -3826891916021780628L;
    private int code;
    private String msg;
    private Object data;

    public Result(Object data) {
        this.code = ResultCode.SUCCESS.getCode();
        this.msg = ResultCode.SUCCESS.getMsg();
        this.data = data;
    }
    public Result(ResultCode resultCode,Object data) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.data = data;
    }
}
package com.ojhelper.back.common;

import lombok.Getter;

@Getter
public enum ResultCode {
    SUCCESS(20231,"success"),
    UN_ERR(20232,"unknown error"),
    FAILED(20233,"failed"),
    VALIDATE_ERROR(20234,"validate error"),
    RESPONSE_PACK_ERROR(20235, "response pack error");

    private int code;
    private String msg;

    ResultCode(int code,String msg) {
        this.code = code;
        this.msg = msg;
    }

}

package com.nowcoder.community.community.controller.exceptionAdvice;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "未找到该资源", 1001),
    REQUEST_VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "请求数据格式验证失败", 1002);


    private final HttpStatus status;

    private final String message;

    private final int code;

    ErrorCode(HttpStatus status, String message, int code) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "ErrorCode{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", code=" + code +
                '}';
    }
}

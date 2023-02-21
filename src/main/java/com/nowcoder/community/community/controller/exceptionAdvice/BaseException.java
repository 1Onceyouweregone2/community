package com.nowcoder.community.community.controller.exceptionAdvice;

import org.springframework.util.ObjectUtils;

import java.util.HashMap;

public class BaseException extends RuntimeException{

    private final ErrorCode error;
    private HashMap<String,Object> data = new HashMap<>();

    public BaseException(ErrorCode error, HashMap<String,Object> data) {
        super(error.getMessage());
        this.error = error;
        if (!ObjectUtils.isEmpty(data)) {
            this.data.putAll(data);
        }
    }

    public BaseException(ErrorCode error, HashMap<String,Object> data,Throwable cause) {
        super(error.getMessage(),cause);
        this.error = error;
        if (!ObjectUtils.isEmpty(data)) {
            this.data.putAll(data);
        }
    }

    public ErrorCode getError() {
        return error;
    }

    public HashMap<String, Object> getData() {
        return data;
    }
}

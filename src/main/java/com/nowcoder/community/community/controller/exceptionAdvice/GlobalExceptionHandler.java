package com.nowcoder.community.community.controller.exceptionAdvice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice(annotations = Controller.class)
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<?> handleAppException(BaseException ex, HttpServletRequest request) {
        ErrorResponse representation = new ErrorResponse(ex,request.getRequestURI());
        return new ResponseEntity<>(representation,new HttpHeaders(),ex.getError().getStatus());
    }

}

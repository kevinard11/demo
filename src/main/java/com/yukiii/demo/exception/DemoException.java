package com.yukiii.demo.exception;

import com.yukiii.demo.constant.AppConstant;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class DemoException extends RuntimeException {
    private HttpStatus status;
    private String code;

    public DemoException(String message) {
        super(message);
    }

    public DemoException(String message, String code, HttpStatus status) {
        super(message);
        this.code = code;
        this.status = status;
    }

    public DemoException(AppConstant.ResponseConstant constant) {
        super(constant.getMessage());
        this.code = constant.getCode();
        this.status = constant.getStatus();
    }

    public DemoException(AppConstant.ResponseConstant constant, String message) {
        super(message);
        this.code = constant.getCode();
        this.status = constant.getStatus();
    }
}

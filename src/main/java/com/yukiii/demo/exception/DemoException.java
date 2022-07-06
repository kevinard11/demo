package com.yukiii.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DemoException extends RuntimeException {
    public DemoException(String message) {
        super(message);
    }
}

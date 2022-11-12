package com.yukiii.demo.adapter.handler;

import com.yukiii.demo.dto.response.DemoExceptionResponse;
import com.yukiii.demo.exception.DemoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
@Slf4j
public class DemoExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<?> handleAllException(Exception ex, WebRequest request){
        var exceptionResponse = new DemoExceptionResponse(new Date(),
                "Something wrong",
                "",
                request.getDescription(false).substring(4),
                "999");
        log.error("Exception : ", ex);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var exceptionResponse = new DemoExceptionResponse(new Date(),
                "Request validation failed",
                ex.getBindingResult().getAllErrors().get(0).getDefaultMessage(),
                request.getDescription(false).substring(4),
                "001");
        log.error("Exception : ", ex);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DemoException.class})
    public final ResponseEntity<?> notFoundException(DemoException ex, WebRequest request){
        var code = ex.getCode() == null ? "999" : ex.getCode();
        var status = ex.getStatus() == null ? HttpStatus.CONFLICT : ex.getStatus();

        var exceptionResponse = new DemoExceptionResponse(new Date(),
                "Something wrong", ex.getMessage(),
                request.getDescription(false).substring(4),
                code);
        log.error("Exception : ", ex);
        return new ResponseEntity<>(exceptionResponse, status);
    }
}

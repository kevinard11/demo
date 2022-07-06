package com.yukiii.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class DemoExceptionResponse {
    private Date timestamp;
    private String message;
    private String detail;
    private String path;
    private String statusCode;
}

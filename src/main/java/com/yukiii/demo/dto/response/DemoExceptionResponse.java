package com.yukiii.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class DemoExceptionResponse extends RepresentationModel {
    private Date timestamp;
    private String message;
    private String detail;
    private String path;
    private String statusCode;
}

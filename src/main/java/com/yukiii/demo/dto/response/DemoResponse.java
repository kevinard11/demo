package com.yukiii.demo.dto.response;

import com.yukiii.demo.constant.AppConstant;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class DemoResponse {
    private Date timestamp;
    private String message;
    private Object data;
    
    public DemoResponse createResponse(Object data){
        this.timestamp = new Date();
        this.message = AppConstant.SUCCESS_RESPONSE;
        this.data = data;
        return this;
    }
}

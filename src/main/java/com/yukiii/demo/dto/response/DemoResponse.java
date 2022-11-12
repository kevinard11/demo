package com.yukiii.demo.dto.response;

import com.yukiii.demo.constant.AppConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DemoResponse<T> {
    private Date timestamp;
    private String message;
    private T data;
    
    public DemoResponse createResponse(T data){
        this.timestamp = new Date();
        this.message = AppConstant.ResponseConstant.SUCCESS.getMessage();
        this.data = data;
        return this;
    }

    public DemoResponse createResponse(AppConstant.ResponseConstant constant){
        this.timestamp = new Date();
        this.message = constant.getMessage();
        return this;
    }

    public DemoResponse createResponse(AppConstant.ResponseConstant constant, T data){
        this.timestamp = new Date();
        this.message = constant.getMessage();
        this.data = data;
        return this;
    }
}

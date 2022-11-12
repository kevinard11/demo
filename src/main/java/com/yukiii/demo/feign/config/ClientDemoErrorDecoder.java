package com.yukiii.demo.feign.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yukiii.demo.constant.AppConstant;
import com.yukiii.demo.exception.DemoException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class ClientDemoErrorDecoder implements ErrorDecoder {

  @Autowired
  ObjectMapper objectMapper;

  @Override
  public Exception decode(String s, Response response) {
    if(response == null){
      throw new DemoException(AppConstant.ResponseConstant.CLIENT_ERROR);
    }

    try{
      String bodyResp = IOUtils.toString(response.body().asInputStream(), StandardCharsets.UTF_8);

      log.info(bodyResp);
    }catch(IOException e){
      throw new DemoException(AppConstant.ResponseConstant.CLIENT_PARSEBODYERROR);
    }

    return new DemoException(AppConstant.ResponseConstant.CLIENT_ERROR);
  }
}

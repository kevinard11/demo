package com.yukiii.demo.feign.config;

import com.yukiii.demo.constant.AppConstant;
import feign.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StreamUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
public class ClientDemoInterceptor extends Client.Default implements RequestInterceptor {

  @Value("${setting.service.internal.key}")
  private String internalKey;

  public ClientDemoInterceptor(SSLSocketFactory sslContextFactory, HostnameVerifier hostnameVerifier) {
    super(sslContextFactory, hostnameVerifier);
  }

  @Override
  public void apply(RequestTemplate requestTemplate) {
    requestTemplate.header(AppConstant.HEADER_SECURITY_API_SECRET, internalKey);
  }

  @Override
  public Response execute(Request request, Request.Options options) throws IOException {
    Response response = super.execute(request, options);
    InputStream bodyStream = response.body().asInputStream();

    String responseBody = StreamUtils.copyToString(bodyStream, StandardCharsets.UTF_8);

    log.info("Response received from {} with body = {}", request.url(), responseBody);

    return super.execute(request, options);
  }
}

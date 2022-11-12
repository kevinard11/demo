package com.yukiii.demo.adapter.auth;

import com.yukiii.demo.constant.AppConstant;
import com.yukiii.demo.controller.auth.AuthController;
import com.yukiii.demo.dto.response.DemoExceptionResponse;
import com.yukiii.demo.util.JsonMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Date;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Configuration
public class DemoAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

  @Override
  public void commence (HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

    var status = AppConstant.ResponseConstant.ACCESS_UNAUTHORIZED;

    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);

    var res =  new DemoExceptionResponse(new Date(),
      status.getMessage(),
      status.getMessage(),
      request.getServletPath(),
      status.getCode());

    res.add(linkTo(methodOn(AuthController.class).signIn(null, null)).withRel("login"));

    try (PrintWriter out = response.getWriter()) {
      JsonMapperUtil.write(out, res);
    }
  }
}

package com.yukiii.demo.adapter.auth;

import com.yukiii.demo.constant.AppConstant;
import com.yukiii.demo.controller.auth.AuthController;
import com.yukiii.demo.dto.response.DemoExceptionResponse;
import com.yukiii.demo.util.JsonMapperUtil;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DemoAuthenticationDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
    var status = AppConstant.ResponseConstant.ACCESS_FORBIDDEN;

    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);

    var res =  new DemoExceptionResponse(new Date(),
      status.getMessage(),
      "Disable to access the api.",
      request.getServletPath(),
      status.getCode());

    res.add(linkTo(methodOn(AuthController.class).signIn(null, null)).withRel("login"));

    try (PrintWriter out = response.getWriter()) {
      JsonMapperUtil.write(out, res);
    }
  }
}

package com.yukiii.demo.controller.auth;

import com.yukiii.demo.constant.AppConstant;
import com.yukiii.demo.dto.auth.AuthSignInDto;
import com.yukiii.demo.dto.auth.AuthTokenDto;
import com.yukiii.demo.dto.response.DemoResponse;
import com.yukiii.demo.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("auth")
@Tag(name = "Authentication", description = "Controller for authentication")
public class AuthController {

  @Autowired
  private AuthService service;

  @RequestMapping(value = "/signin", method = RequestMethod.POST)
  @Operation(summary = "Auth sign in", operationId = "Authentication")
  public DemoResponse<AuthTokenDto> signIn(
    @RequestHeader(value = AppConstant.HEADER_SECURITY_API_SECRET, required = false) String secret,
    @Valid @RequestBody AuthSignInDto dto
  ){
    service.signIn(dto);
    var res = service.generateToken(dto.getUsername());

    return new DemoResponse<AuthTokenDto>().createResponse(res);
  }
}

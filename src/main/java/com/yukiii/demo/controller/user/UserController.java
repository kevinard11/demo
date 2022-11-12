package com.yukiii.demo.controller.user;

import com.yukiii.demo.constant.AppConstant;
import com.yukiii.demo.dto.kyc.KycCreateDto;
import com.yukiii.demo.dto.response.DemoResponse;
import com.yukiii.demo.dto.user.UserCreateDto;
import com.yukiii.demo.dto.user.UserReadDto;
import com.yukiii.demo.exception.DemoException;
import com.yukiii.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("user")
@Tag(name = "User", description = "Controller for user")
public class UserController {

  @Autowired
  @Qualifier("userServiceImpl")
  UserService service;

  @RequestMapping(method = RequestMethod.POST)
  @Operation(summary = "Create a user", operationId = "User")
  public DemoResponse createUser(
    @RequestHeader(value = AppConstant.HEADER_SECURITY_API_SECRET, required = false) String secret,
    @Valid @RequestBody UserCreateDto dto
  ){
    service.createUser(dto);
    return new DemoResponse<>().createResponse(AppConstant.ResponseConstant.CREATE_SUCCESS);
  }

  @RequestMapping(value = "{id}", method = RequestMethod.GET)
  @Operation(summary = "Get a user", operationId = "User")
  public DemoResponse<UserReadDto> getUser(
    @RequestHeader(value = AppConstant.HEADER_SECURITY_API_SECRET, required = false) String secret,
    @PathVariable(name = "id") Long id
  ){
    var res = service.getUser(id);

    if(res == null){
      throw new DemoException(AppConstant.ResponseConstant.DATA_NOTFOUND, "User not found");
    }
    return new DemoResponse<UserReadDto>().createResponse(res);
  }

  @RequestMapping(value = "{id}/kyc", method = RequestMethod.PUT)
  @Operation(summary = "Update kyc for user", operationId = "User")
  public DemoResponse updateKycUser(
    @RequestHeader(value = AppConstant.HEADER_SECURITY_API_SECRET, required = false) String secret,
    @PathVariable(name = "id") Long id,
    @Valid @RequestBody KycCreateDto dto
  ){
    service.updateKycUser(id, dto);
    return new DemoResponse<>().createResponse(AppConstant.ResponseConstant.CREATE_SUCCESS);
  }

  @RequestMapping(value = "{id}/demo/{demoId}", method = RequestMethod.PUT)
  @Operation(summary = "Update demo for user", operationId = "User")
  public DemoResponse updateDemoUser(
    @RequestHeader(value = AppConstant.HEADER_SECURITY_API_SECRET, required = false) String secret,
    @PathVariable(name = "id") Long id,
    @PathVariable(name = "demoId") Long demoId
  ){
    service.updateDemoUser(id, demoId);
    return new DemoResponse<>().createResponse(AppConstant.ResponseConstant.CREATE_SUCCESS);
  }

  @RequestMapping(value = "role", method = RequestMethod.GET)
  @Operation(summary = "Get list of user by list of role", operationId = "User")
  public DemoResponse getUserByRoles(
    @RequestHeader(value = AppConstant.HEADER_SECURITY_API_SECRET, required = false) String secret,
    @RequestParam(name = AppConstant.PARAM_ROLE, defaultValue = "") List<Long> roleIds
  ){
    var res = service.getUserByRoles(roleIds);

    if(res == null){
      throw new DemoException(AppConstant.ResponseConstant.DATA_NOTFOUND, "User not found");
    }
    return new DemoResponse<List<UserReadDto>>().createResponse(res);
  }
}

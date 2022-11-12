package com.yukiii.demo.controller.demo;

import com.yukiii.demo.constant.AppConstant;
import com.yukiii.demo.dto.demo.DemoCreateDto;
import com.yukiii.demo.dto.demo.DemoReadDto;
import com.yukiii.demo.dto.response.DemoResponse;
import com.yukiii.demo.exception.DemoException;
import com.yukiii.demo.service.DemoService;
import com.yukiii.demo.util.PagingUtil;
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
@RequestMapping("demo")
@Tag(name = "Demo", description = "Controller for demo")
public class DemoController {

    @Autowired
    @Qualifier("DemoServiceImpl")
    DemoService service;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @Operation(summary = "Test demo", operationId = "Demo")
    public String demo(){

        log.info("get demo successfully");
        return "Welcome to yukiii demo";
    }

    @RequestMapping(method = RequestMethod.POST)
    @Operation(summary = "Create a demo", operationId = "Demo")
    public DemoResponse createDemo(
            @RequestHeader(value = AppConstant.HEADER_SECURITY_API_SECRET, required = false) String secret,
            @Valid @RequestBody DemoCreateDto dto
    ){
        service.createDemo(dto);
        return new DemoResponse().createResponse(AppConstant.ResponseConstant.CREATE_SUCCESS);
    }

    @RequestMapping(method = RequestMethod.GET)
    @Operation(summary = "Get all demo", operationId = "Demo")
    public DemoResponse getAllDemo(
      @RequestHeader(value = AppConstant.HEADER_SECURITY_API_SECRET, required = false) String secret,
      @RequestParam(name = "page", defaultValue = "1", required = false) int page,
      @RequestParam(name = "size", defaultValue = "20", required = false) int size,
      @RequestParam(name = "sort", defaultValue = "id.asc", required = false) List<String> sort
    ){
        var res = service.getAllDemo(PagingUtil.buildPaginationUtil(page - 1, size, sort));
        if (res == null){
            throw new DemoException(AppConstant.ResponseConstant.DATA_NOTFOUND);
        }

        return new DemoResponse().createResponse(res);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @Operation(summary = "Get a demo", operationId = "Demo")
    public DemoResponse<DemoReadDto> getDemo(
            @RequestHeader(value = AppConstant.HEADER_SECURITY_API_SECRET, required = false) String secret,
            @PathVariable(name = "id") Long id
    ){
        var res = service.getDemo(id);
        if (res == null){
            throw new DemoException(AppConstant.ResponseConstant.DATA_NOTFOUND);
        }

        service.sendMqDemo(res);
        return new DemoResponse<DemoReadDto>().createResponse(res);
    }

    @RequestMapping(value = "filter", method = RequestMethod.GET)
    @Operation(summary = "Get demo with filter", operationId = "Demo")
    public DemoResponse<List<DemoReadDto>> getDemoByFilter(
      @RequestHeader(value = AppConstant.HEADER_SECURITY_API_SECRET, required = false) String secret,
      @RequestParam(name = "demoId", defaultValue = "", required = false) List<String> demoId,
      @RequestParam(name = "demoName", defaultValue = "", required = false) String demoName,
      @RequestParam(name = "level", required = false) Integer level
    ){
        if (demoId.isEmpty() && demoName.isEmpty() && level == null){
            throw new DemoException(AppConstant.ResponseConstant.PARAM_INVALID);
        }

        var res = service.getDemoByFilter(demoId, demoName, level);
        if (res == null){
            throw new DemoException(AppConstant.ResponseConstant.DATA_NOTFOUND);
        }

        return new DemoResponse<List<DemoReadDto>>().createResponse(res);
    }
}

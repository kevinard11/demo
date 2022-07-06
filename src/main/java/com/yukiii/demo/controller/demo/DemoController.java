package com.yukiii.demo.controller.demo;

import com.yukiii.demo.dto.demo.DemoCreateDto;
import com.yukiii.demo.dto.response.DemoResponse;
import com.yukiii.demo.service.DemoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("demo")
@Tag(name = "Demo", description = "Controller for demo")
public class DemoController {

    @Autowired
    DemoService service;

    @RequestMapping(method = RequestMethod.GET)
    public String demo(){

        log.info("get demo successfully");
        return "Welcome to yukiii demo";
    }

    @RequestMapping(method = RequestMethod.POST)
    @Operation(summary = "Create a demo", operationId = "Demo")
    public DemoResponse createDemo(
            @RequestHeader(value = "secret", required = false) String secret,
            @RequestBody DemoCreateDto dto
    ){
        service.createDemo(dto);
        return new DemoResponse().createResponse(null);
    }
}

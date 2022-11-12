package com.yukiii.demo.service;

import com.yukiii.demo.dto.demo.DemoCreateDto;
import com.yukiii.demo.dto.demo.DemoReadDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DemoService {

    void createDemo(DemoCreateDto dto);
    Page<DemoReadDto> getAllDemo(Pageable pageable);
    DemoReadDto getDemo(Long id);
    List<DemoReadDto> getDemoByFilter(List<String> demoId, String demoName, Integer level);

    void callFeignDemo(Long id);
    void sendMqDemo(DemoReadDto demo);
}

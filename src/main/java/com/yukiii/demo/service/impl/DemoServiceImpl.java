package com.yukiii.demo.service.impl;

import com.yukiii.demo.dto.demo.DemoCreateDto;
import com.yukiii.demo.mapper.DemoMapper;
import com.yukiii.demo.repository.DemoRepository;
import com.yukiii.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("demoServiceImpl")
public class DemoServiceImpl implements DemoService {

    @Autowired
    private DemoRepository repository;

    @Override
    public void createDemo(DemoCreateDto dto) {
        var demo = DemoMapper.INSTANCE.createDtoToDemo(dto);
        demo.setVersion(1);
        repository.save(demo);
    }
}

package com.yukiii.demo.service.impl;

import com.yukiii.demo.cache.impl.DemoCache;
import com.yukiii.demo.connector.DemoPublisher;
import com.yukiii.demo.dto.demo.DemoCreateDto;
import com.yukiii.demo.dto.demo.DemoReadDto;
import com.yukiii.demo.feign.DemoClient;
import com.yukiii.demo.mapper.DemoMapper;
import com.yukiii.demo.repository.DemoRepository;
import com.yukiii.demo.service.DemoService;
import com.yukiii.demo.util.SpecificationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service("DemoServiceImpl")
public class DemoServiceImpl implements DemoService {

    @Autowired
    private DemoRepository repository;

    @Autowired
    private DemoCache demoCacheService;

    @Autowired
    private DemoClient demoClient;

    @Autowired
    private DemoPublisher demoPublisher;

    @Override
    public void createDemo(DemoCreateDto dto) {
        var demo = DemoMapper.INSTANCE.createDtoToDemo(dto);
        demo.setVersion(1);
        repository.save(demo);
    }

    @Override
    public Page<DemoReadDto> getAllDemo(Pageable pageable) {
        var demo = repository.findAll(pageable);

        if(demo.isEmpty()){
            return null;
        }

        var res = demo
          .getContent()
          .stream()
          .map(DemoMapper.INSTANCE::demoToReadDto)
          .collect(Collectors.toList());

        return new PageImpl<>(res, pageable, demo.getTotalElements());
    }

    @Override
    public DemoReadDto getDemo(Long id) {
        var demo = demoCacheService.get(String.valueOf(id));

        if(demo == null){
            var demoO = repository.findById(id);

            if(demoO.isEmpty()) {
                return null;
            }
            
            demo = demoO.get();
            demoCacheService.set(String.valueOf(id), demo);
        }

        return DemoMapper.INSTANCE.demoToReadDto(demo);
    }

    @Override
    @Cacheable("demoCache")
    public List<DemoReadDto> getDemoByFilter(List<String> demoId, String demoName, Integer level) {
        var demo = repository.findAll(
          Specification
            .where(
              Specification
                .where(SpecificationUtil.hasDemoIds(demoId))
                .or(SpecificationUtil.hasDemoName(demoName))
            )
            .and(SpecificationUtil.hasLevel(level))
            .and(SpecificationUtil.isActive())
        );

        return demo
          .stream()
          .map(DemoMapper.INSTANCE::demoToReadDto)
          .collect(Collectors.toList());
    }

    @Override
    public void callFeignDemo(Long id) {
        demoClient.getDemoById(id);
    }

    @Override
    public void sendMqDemo(DemoReadDto demo) {
        demoPublisher.send(demo, UUID.randomUUID().toString());
    }


}

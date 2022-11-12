package com.yukiii.demo.mapper;

import com.yukiii.demo.dto.demo.DemoCreateDto;
import com.yukiii.demo.dto.demo.DemoReadDto;
import com.yukiii.demo.entity.Demo;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface DemoMapper {
    DemoMapper INSTANCE = Mappers.getMapper(DemoMapper.class);

    Demo createDtoToDemo(DemoCreateDto dto);
    DemoReadDto demoToReadDto(Demo demo);
}

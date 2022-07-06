package com.yukiii.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface DemoMapper {
    DemoMapper INSTANCE = Mappers.getMapper(DemoMapper.class);

    
}

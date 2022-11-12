package com.yukiii.demo.mapper;

import com.yukiii.demo.dto.role.RoleReadDto;
import com.yukiii.demo.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface RoleMapper {
  RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

  RoleReadDto roleToReadDto(Role role);
}

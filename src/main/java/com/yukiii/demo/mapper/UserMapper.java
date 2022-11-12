package com.yukiii.demo.mapper;

import com.yukiii.demo.dto.user.UserCreateDto;
import com.yukiii.demo.dto.user.UserReadDto;
import com.yukiii.demo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {
  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  User createDtoToUser(UserCreateDto dto);
  UserReadDto userToReadDto(User user);
}

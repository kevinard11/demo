package com.yukiii.demo.mapper;

import com.yukiii.demo.dto.kyc.KycCreateDto;
import com.yukiii.demo.entity.Kyc;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface KycMapper {
  KycMapper INSTANCE = Mappers.getMapper(KycMapper.class);

  Kyc createDtoToKyc(KycCreateDto dto);
}

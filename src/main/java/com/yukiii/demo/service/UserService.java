package com.yukiii.demo.service;

import com.yukiii.demo.dto.kyc.KycCreateDto;
import com.yukiii.demo.dto.user.UserCreateDto;
import com.yukiii.demo.dto.user.UserReadDto;

import java.util.List;

public interface UserService {

  void createUser(UserCreateDto dto);
  void updateKycUser(Long id, KycCreateDto dto);
  void updateDemoUser(Long id, Long demoId);
  UserReadDto getUser(Long id);
  List<UserReadDto> getUserByRoles(List<Long> roleIds);
}

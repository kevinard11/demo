package com.yukiii.demo.service.impl;

import com.yukiii.demo.constant.AppConstant;
import com.yukiii.demo.dto.kyc.KycCreateDto;
import com.yukiii.demo.dto.user.UserCreateDto;
import com.yukiii.demo.dto.user.UserReadDto;
import com.yukiii.demo.exception.DemoException;
import com.yukiii.demo.mapper.DemoMapper;
import com.yukiii.demo.mapper.KycMapper;
import com.yukiii.demo.mapper.UserMapper;
import com.yukiii.demo.repository.DemoRepository;
import com.yukiii.demo.repository.UserRepository;
import com.yukiii.demo.service.UserService;
import com.yukiii.demo.util.CryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository repository;

  @Autowired
  private DemoRepository demoRepository;

  @Override
  public void createUser(UserCreateDto dto) {
    var user = repository.findByUsername(dto.getUsername());

    if(user != null){
      throw new DemoException(AppConstant.ResponseConstant.DATA_ALREADYEXIST);
    }

    dto.setPassword(CryptUtil.encodeBCrypt(dto.getPassword()));
    user = UserMapper.INSTANCE.createDtoToUser(dto);

    repository.save(user);
  }

  @Override
  public void updateKycUser(Long id, KycCreateDto dto) {
    var userO = repository.findById(id);

    if(userO.isEmpty()){
      throw new DemoException(AppConstant.ResponseConstant.DATA_NOTFOUND, "User not found");
    }

    var user = userO.get();
    var kyc = KycMapper.INSTANCE.createDtoToKyc(dto);

    user.setKyc(kyc);
    repository.save(user);
  }

  @Override
  public void updateDemoUser(Long id, Long demoId) {
    var userO = repository.findById(id);

    if(userO.isEmpty()){
      throw new DemoException(AppConstant.ResponseConstant.DATA_NOTFOUND, "User not found");
    }

    var user = userO.get();

    var demoO = demoRepository.findById(demoId);
    if(demoO.isEmpty()){
      throw new DemoException(AppConstant.ResponseConstant.DATA_NOTFOUND, "Demo not found");
    }

    var demo = demoO.get();
    demo.setUser(user);

    demoRepository.save(demo);
  }

  @Override
  public UserReadDto getUser(Long id) {
    var userO = repository.findById(id);

    if(userO.isEmpty()){
      return null;
    }

    var user = userO.get();

    return UserMapper.INSTANCE.userToReadDto(user);
  }

  @Override
  public List<UserReadDto> getUserByRoles(List<Long> roleIds) {
    var users = repository.findUserByRoleIn(roleIds);

    return users
      .stream()
      .map(UserMapper.INSTANCE::userToReadDto)
      .collect(Collectors.toList());
  }
}

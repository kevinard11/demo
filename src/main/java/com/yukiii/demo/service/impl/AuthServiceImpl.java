package com.yukiii.demo.service.impl;

import com.yukiii.demo.adapter.auth.JwtService;
import com.yukiii.demo.constant.AppConstant;
import com.yukiii.demo.dto.auth.AuthSignInDto;
import com.yukiii.demo.dto.auth.AuthTokenDto;
import com.yukiii.demo.exception.DemoException;
import com.yukiii.demo.repository.UserRepository;
import com.yukiii.demo.service.AuthService;
import com.yukiii.demo.util.CryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("authServiceImpl")
public class AuthServiceImpl implements AuthService {

  @Autowired
  private UserRepository repository;

  @Autowired
  private JwtService jwtService;

  @Override
  public void signIn(AuthSignInDto dto) {

    var user = repository.findByUsername(dto.getUsername());
    if(user == null){
      throw new DemoException(AppConstant.ResponseConstant.DATA_NOTFOUND);
    }

    if(!CryptUtil.isPasswordMatch(dto.getPassword(), user.getPassword())){
      throw new DemoException(AppConstant.ResponseConstant.DATA_NOTMATCH);
    }
  }

  @Override
  public AuthTokenDto generateToken(String username){
    String token  = jwtService.generateToken(username);

    return AuthTokenDto
      .builder()
      .token(token)
      .build();
  }
}

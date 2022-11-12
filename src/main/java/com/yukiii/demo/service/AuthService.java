package com.yukiii.demo.service;

import com.yukiii.demo.dto.auth.AuthSignInDto;
import com.yukiii.demo.dto.auth.AuthTokenDto;

public interface AuthService {

  void signIn(AuthSignInDto dto);
  AuthTokenDto generateToken(String username);
}

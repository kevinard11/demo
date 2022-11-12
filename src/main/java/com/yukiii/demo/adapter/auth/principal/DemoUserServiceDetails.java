package com.yukiii.demo.adapter.auth.principal;

import com.yukiii.demo.constant.AppConstant;
import com.yukiii.demo.exception.DemoException;
import com.yukiii.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service("demoUserServiceDetails")
public class DemoUserServiceDetails implements UserDetailsService {

  @Autowired
  private UserRepository repository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var user = repository.findByUsername(username);

    if(user == null){
      throw new DemoException(AppConstant.ResponseConstant.DATA_NOTFOUND);
    }

    return DemoUserDetails
      .builder()
      .username(user.getUsername())
      .password(user.getPassword())
      .authorities(DemoUserDetails.buildAuthorityWithAuthority(user.getRole()))
      .build();
  }
}

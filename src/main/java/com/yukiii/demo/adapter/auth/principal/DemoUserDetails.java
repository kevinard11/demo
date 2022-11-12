package com.yukiii.demo.adapter.auth.principal;

import com.yukiii.demo.constant.AppConstant;
import com.yukiii.demo.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DemoUserDetails implements UserDetails {

  private String username;
  private String password;
  private Collection<? extends GrantedAuthority> authorities;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return false;
  }

  public static List<GrantedAuthority> buildAuthorityWithAuthority(List<Role> roles){

    return roles
      .stream()
      .map(
        role -> new DemoGrantedAuthority(role.getRole())
      ).collect(Collectors.toList());
  }

  public static List<GrantedAuthority> buildAuthorityWithRole(List<Role> roles){

    return roles
      .stream()
      .map(
        role -> new DemoGrantedAuthority(AppConstant.PREFIX_AUTH_ROLE + role.getRole())
      ).collect(Collectors.toList());
  }
}


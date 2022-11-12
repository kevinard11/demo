package com.yukiii.demo.adapter.auth.principal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DemoGrantedAuthority implements GrantedAuthority {
  private String authority;

  @Override
  public String getAuthority() {
    return this.authority;
  }
}

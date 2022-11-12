package com.yukiii.demo.adapter.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public interface SecurityContextService {

    AbstractAuthenticationToken decodeApiSecret(String key);
    UsernamePasswordAuthenticationToken decodeBearerToken(String token);
}

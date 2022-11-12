package com.yukiii.demo.adapter.auth.impl;

import com.yukiii.demo.adapter.auth.JwtService;
import com.yukiii.demo.adapter.auth.SecurityContextService;
import com.yukiii.demo.adapter.auth.principal.DemoUserServiceDetails;
import com.yukiii.demo.constant.AppConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.intercept.RunAsUserToken;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SecurityContextServiceImpl implements SecurityContextService {

    @Value("${setting.service.internal.key}")
    private String internalKey;

    @Value("${setting.service.internal.name}")
    private String internalName;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private DemoUserServiceDetails userServiceDetails;

    @Override
    public AbstractAuthenticationToken decodeApiSecret(String key) {
        AbstractAuthenticationToken userToken = null;

        if (StringUtils.hasText(key) && key.equals(internalKey)){

            userToken =
              new RunAsUserToken(
                key,
                internalName,
                internalName,
                Arrays.stream(AppConstant.AuthenticationClaimer.values()).map( x ->
                  new SimpleGrantedAuthority(x.toString())
                ).collect(Collectors.toList()),
                null
              );
        }

        return userToken;
    }

    @Override
    public UsernamePasswordAuthenticationToken decodeBearerToken(String token) {
        token = token.substring(7);

        var username = jwtService.getUsernameFromToken(token);

//        var role = jwtService.getRolesFromToken(token);

        var userDetails = userServiceDetails.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(
          userDetails, null, userDetails.getAuthorities());
    }
}

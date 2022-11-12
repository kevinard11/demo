package com.yukiii.demo.adapter.auth;

import com.yukiii.demo.adapter.auth.principal.DemoUserServiceDetails;
import com.yukiii.demo.constant.AppConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class DemoAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private SecurityContextService authService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private DemoUserServiceDetails userServiceDetails;

    @Value("${spring.profile.no-prod}")
    private List<String> profileNoProd;

    @Autowired
    private Environment environment;

    public DemoAuthenticationFilter(SecurityContextService authService) {
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String tokenHeader = request.getHeader(AppConstant.HEADER_SECURITY_API_AUTHORIZATION);
        String apiSecretHeader = request.getHeader(AppConstant.HEADER_SECURITY_API_SECRET);

        if(tokenHeader != null){
            if(tokenHeader.startsWith(AppConstant.HEADER_SECURITY_API_AUTHORIZATION_STARTS)){
                SecurityContextHolder
                  .getContext()
                  .setAuthentication(authService.decodeBearerToken(tokenHeader));
            }
        }else if (apiSecretHeader != null && profileNoProd.containsAll(Arrays.asList(environment.getActiveProfiles()))){
            SecurityContextHolder
              .getContext()
              .setAuthentication(authService.decodeApiSecret(apiSecretHeader));
        }

        filterChain.doFilter(request, response);
    }
}

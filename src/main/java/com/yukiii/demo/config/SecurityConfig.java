package com.yukiii.demo.config;

import com.yukiii.demo.adapter.auth.DemoAuthenticationDeniedHandler;
import com.yukiii.demo.adapter.auth.DemoAuthenticationEntryPoint;
import com.yukiii.demo.adapter.auth.DemoAuthenticationFilter;
import com.yukiii.demo.adapter.auth.principal.DemoUserServiceDetails;
import com.yukiii.demo.constant.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DemoAuthenticationEntryPoint demoAuthenticationEntryPoint;

    @Autowired
    private DemoAuthenticationFilter demoAuthenticationFilter;

    @Autowired
    private DemoAuthenticationDeniedHandler demoAuthenticationDeniedHandler;

    @Autowired
    private DemoUserServiceDetails userServiceDetails;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userServiceDetails).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean("authenticationManagerBean")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
          .csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
          .and()
          .exceptionHandling().authenticationEntryPoint(demoAuthenticationEntryPoint).accessDeniedHandler(demoAuthenticationDeniedHandler)
          .and()
          .authorizeRequests()
          .antMatchers("/swagger*/**", "/api-docs/**", "/demo/test", "/auth/**").permitAll()
          .antMatchers("/user/**").hasAnyAuthority(AppConstant.AuthenticationClaimer.USER.toString(), AppConstant.AuthenticationClaimer.SYSTEM_SERVICE.toString())
          .antMatchers("/demo/**").hasAnyAuthority(AppConstant.AuthenticationClaimer.ADMIN.toString(), AppConstant.AuthenticationClaimer.SYSTEM_SERVICE.toString())
          .anyRequest().authenticated()
        ;

        http
          .addFilterBefore(demoAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        ;

    }
}

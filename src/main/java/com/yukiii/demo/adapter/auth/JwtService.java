package com.yukiii.demo.adapter.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yukiii.demo.adapter.auth.principal.DemoGrantedAuthority;
import com.yukiii.demo.adapter.auth.principal.DemoUserServiceDetails;
import com.yukiii.demo.constant.AppConstant;
import com.yukiii.demo.exception.DemoException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service("jwtService")
public class JwtService {

  @Value("${setting.service.internal.key}")
  private String internalKey;

  @Value("${setting.token.jwt.ttl-minutes}")
  private int tokenTtlMinutes;

  @Autowired
  private DemoUserServiceDetails userServiceDetails;

  @Autowired
  private ObjectMapper mapper;

  public String generateToken(String username){
    var user = userServiceDetails.loadUserByUsername(username);

    return generateTokenWithAuthority(user);
  }

  public String generateTokenWithRole(UserDetails user){
    Map<String, Object> claims = new HashMap<>();

    claims.put(AppConstant.PARAM_ROLE, createRoleClaim(user.getAuthorities()));

    return generateToken(user.getUsername(), claims, tokenTtlMinutes, TimeUnit.DAYS);
  }

  public String generateTokenWithAuthority(UserDetails user){
    Map<String, Object> claims = new HashMap<>();

    claims.put(AppConstant.PARAM_ROLE, user.getAuthorities());

    return generateToken(user.getUsername(), claims, tokenTtlMinutes, TimeUnit.DAYS);
  }

  private String generateToken(String subject, Map<String, Object> claims, long duration, TimeUnit unit) {
    var issuedAt = System.currentTimeMillis();

    return Jwts
      .builder()
      .setClaims(claims)
      .setSubject(subject)
      .setIssuedAt(new Date(issuedAt))
      .setExpiration(new Date(issuedAt + (unit.toSeconds(duration) * 1000)))
      .signWith(SignatureAlgorithm.HS512, internalKey)
      .compact();
  }

  public Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  public String getUsernameFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  private Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(internalKey).parseClaimsJws(token).getBody();
  }

  public List<DemoGrantedAuthority> getRolesFromTokenWithRole(String token) {
    var claims = getAllClaimsFromToken(token);

    var roles = claims.get(AppConstant.PARAM_ROLE, String.class).split(",");

    return Arrays
      .stream(roles)
      .map(DemoGrantedAuthority::new)
      .collect(Collectors.toList());
  }

  public List<DemoGrantedAuthority> getRolesFromTokenWithAuthority(String token){
    var claims = getAllClaimsFromToken(token);

    var role =  claims.get(AppConstant.PARAM_ROLE, List.class);

    try{
      var authority = mapper.writeValueAsString(role);
      log.info(authority);

      return null;
    }catch(JsonProcessingException e){
      throw new DemoException(e.getMessage());
    }
  }
  private String createRoleClaim(Collection<? extends GrantedAuthority> grantedAuthorities){
    return grantedAuthorities.stream()
      .map(GrantedAuthority::getAuthority)
      .collect(Collectors.joining(","));
  }
}

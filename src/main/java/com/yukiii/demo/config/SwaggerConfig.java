package com.yukiii.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yukiii.demo.constant.AppConstant;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig{

  @Bean
  public OpenAPI openAPI(@Value("${springdoc.version}") String appVersion, ObjectMapper objectMapper){
    ModelConverters.getInstance().addConverter(new ModelResolver(objectMapper));
    return new OpenAPI()
      .components(
        new Components()
      )
      .addSecurityItem(new SecurityRequirement().addList(AppConstant.PARAM_AUTH_BEARER))
      .components(
        new Components()
          .addSecuritySchemes(AppConstant.PARAM_AUTH_BEARER,
            new SecurityScheme()
              .name(AppConstant.PARAM_AUTH_BEARER)
              .type(SecurityScheme.Type.HTTP)
              .scheme(AppConstant.PARAM_AUTH_BEARER)
              .bearerFormat(AppConstant.PARAM_AUTH_JWT)
          )
      )
      .info(
        new Info()
          .title("com.yukiii")
          .version(appVersion)
          .license(new License().name("Apache 2.0").url("https://springdoc.org"))
      );
  }

}

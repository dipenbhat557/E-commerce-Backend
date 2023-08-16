package com.eCom.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SpringDocConfig {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Bean
    public SecurityRequirement securityRequirement() {
        SecurityRequirement requirement = new SecurityRequirement();
        requirement.addList(AUTHORIZATION_HEADER);
        return requirement;
    }

    @Bean
    public SecurityScheme securityScheme() {
        SecurityScheme scheme = new SecurityScheme();
        scheme.setType(SecurityScheme.Type.APIKEY);
        scheme.setIn(SecurityScheme.In.HEADER);
        scheme.setName(AUTHORIZATION_HEADER);
        return scheme;
    }
}

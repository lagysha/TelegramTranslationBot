package com.example.apigateway.config;

import com.example.apigateway.filter.AuthTokenValidationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    AuthTokenValidationFilter authTokenValidationFilter;

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception {
        // authorize requests
        http.authorizeExchange()
                .pathMatchers("/silly").permitAll()
                .pathMatchers("/user/**").authenticated();

        // filters
        http.addFilterAfter(authTokenValidationFilter, SecurityWebFiltersOrder.AUTHENTICATION);

        // todo implement cors and (if needed) csrf
        http.csrf().disable();

        return http.build();
    }
}

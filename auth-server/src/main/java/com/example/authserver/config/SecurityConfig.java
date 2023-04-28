package com.example.authserver.config;

import com.example.authserver.filter.JwtTokenGeneratorFilter;
import com.example.securityutils.filter.JwtTokenValidationFilter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@Log4j2
public class SecurityConfig {

    @Value("${utils.security.jwtKey}")
    private String jwtKey;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // auth paths
        http.authorizeHttpRequests()
            .requestMatchers("/auth/register").permitAll()
            .requestMatchers("/auth/user").authenticated();

        // filters
        // jwtTokenValidationFilter - filter for validation requests with jwt (auth) tokens
        // jwtTokenGeneratorFilter - filter that creates JWT token after successful authentication
        http.addFilterBefore(jwtTokenValidationFilter(), BasicAuthenticationFilter.class)
            .addFilterAfter(jwtTokenGeneratorFilter(), BasicAuthenticationFilter.class);

        http.securityContext().requireExplicitSave(false)
            .and()
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //TODO: implement (if needed) csrf
        http.csrf().disable()
            .httpBasic();

        return http.build();
    }

    @Bean
    JwtTokenValidationFilter jwtTokenValidationFilter() {
        var token = new JwtTokenValidationFilter();
        token.setJwtKey(jwtKey);
        return token;
    }

    @Bean
    JwtTokenGeneratorFilter jwtTokenGeneratorFilter() {
        return new JwtTokenGeneratorFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

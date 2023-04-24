package com.example.apigateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@Log4j2
public class AuthTokenValidationFilter implements WebFilter {

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        var request = exchange.getRequest();
        List<String> authToken = request.getHeaders().get("Authorization");

        if (authToken != null && !authToken.isEmpty()) {
            String jwt = authToken.get(0);

            try {
                SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJwt(jwt)
                        .getBody();

                String jwtUsername = (String) claims.get("username");
                String jwtAuthorities = (String) claims.get("authorities");

                Authentication auth = new UsernamePasswordAuthenticationToken(jwtUsername, null,
                        AuthorityUtils.commaSeparatedStringToAuthorityList(jwtAuthorities));

                SecurityContext securityContext = SecurityContextHolder.getContext();
                securityContext.setAuthentication(auth);

                log.info("auth success: " + jwtUsername);

            } catch (Exception e) {
                throw new BadCredentialsException("Invalid token received!" + e.getMessage());
            }
        }
        return chain.filter(exchange);
    }
}

package com.example.apigateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
@Log4j2
public class AuthTokenValidationFilter implements WebFilter {

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        var request = exchange.getRequest();
        String jwt = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (jwt != null && !jwt.startsWith("Basic")) {
            try {
                SecretKey key = Keys.hmacShaKeyFor(
                        secretKey.getBytes(StandardCharsets.UTF_8));

                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();

                String jwtUsername = String.valueOf(claims.get("username"));
                String jwtAuthorities = (String) claims.get("authorities");

                // Save Authentication details & manually set the SecurityContext containing the Authentication in the HTTP session
                Authentication auth = new UsernamePasswordAuthenticationToken(jwtUsername, null,
                        AuthorityUtils.commaSeparatedStringToAuthorityList(jwtAuthorities));

                return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
            }
            catch (Exception e) {
                throw new BadCredentialsException("Invalid token received!" + e.getMessage());
            }
        }
        return chain.filter(exchange);
    }
}

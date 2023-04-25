package com.example.authserver.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Log4j2
public class JwtTokenGeneratorFilter extends OncePerRequestFilter {

    @Value("${utils.security.jwtKey}")
    private String jwtToken;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            // creating jwt token and adding it to the response header
            Date now = new Date();
            SecretKey key = Keys.hmacShaKeyFor(jwtToken.getBytes());

            String jwt = Jwts.builder()
                    .claim("username", authentication.getName())
                    .claim("authorities", authoritiesToString(authentication.getAuthorities()))
                    .setIssuedAt(now)
                    .setExpiration(new Date(now.getTime() + 864000000))
                    .signWith(key)
                    .compact();

            response.setHeader("Authorization", jwt);
            log.info("Setting jwt token in Authorization header");
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getServletPath().equals("/auth/user");
    }

    private String authoritiesToString(Collection<? extends GrantedAuthority> authoritiesCollection) {
        List<String> authorities = authoritiesCollection.stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return String.join(",", authorities);
    }
}

package com.example.securityutils.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class JwtTokenValidationFilter extends OncePerRequestFilter {

    private String jwtKey;

    public void setJwtKey(String jwtKey) {
        this.jwtKey = jwtKey;
    }

    public String getJwtKey() {
        return jwtKey;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = request.getHeader("Authorization");

        if (jwt != null && !jwt.startsWith("Basic")) {
            try {
                SecretKey key = Keys.hmacShaKeyFor(
                        jwtKey.getBytes(StandardCharsets.UTF_8));

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

                SecurityContext securityContext = SecurityContextHolder.getContext();
                securityContext.setAuthentication(auth);
            } catch (Exception e) {
                throw new BadCredentialsException("Invalid Token received!" + e.getMessage());
            }

        }
        filterChain.doFilter(request, response);
    }
}

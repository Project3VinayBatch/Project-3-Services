package com.revature.initiative.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
public class TokenFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final JwTokenUtil jwtTokenUtil;

    @Autowired
    public TokenFilter(JwtProvider jwtProvider, JwTokenUtil jwtTokenUtil) {
        this.jwtProvider = jwtProvider;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authToken = request.getHeader("Authorization");
        if (authToken != null) {
            String token = authToken.split(" ")[1];
            try {
                Jws<Claims> claimsJws = Jwts.parser()
                        .setSigningKey(jwtTokenUtil.getSecretKey())
                        .parseClaimsJws(token);
                String role_prefix = "ROLE_";
                Claims body = claimsJws.getBody();
                //String uid = body.getSubject();
                String username = body.get("userName").toString();
                String role = (String) body.get("role");
                role = role_prefix + role;

                SimpleGrantedAuthority authority = new SimpleGrantedAuthority((String) role);

                Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, Collections.singletonList(authority));
                    SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (JwtException e) {
                throw new IllegalStateException("token cannot be trusted");
            }
            //jwtProvider.getAuth(token);

        }
        filterChain.doFilter(request, response);
    }
}

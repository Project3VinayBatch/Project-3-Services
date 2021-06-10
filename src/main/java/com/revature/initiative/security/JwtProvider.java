package com.revature.initiative.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtProvider {

    private Key secretKey;
    private JwTokenUtil jwTokenUtil;
    private final Map<String, Authentication> cache = new HashMap<>();

    @Autowired
    public JwtProvider(JwTokenUtil jwTokenUtil) {
        this.jwTokenUtil = jwTokenUtil;
    }

    @PostConstruct
    public void init() {
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    public String generateToken(Authentication authentication) {
        String token = jwTokenUtil.generateToken(authentication);
        cache.put(token, authentication);
        return token;
    }

    public Authentication getAuth(String token) {
        return cache.getOrDefault(token, null);
    }
}

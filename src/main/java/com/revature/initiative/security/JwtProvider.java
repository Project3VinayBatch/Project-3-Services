package com.revature.initiative.security;

import com.revature.initiative.dto.UserDTO;
import com.revature.initiative.model.User;
import com.revature.initiative.service.UserService;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
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
    private final UserService userService;

    @Autowired
    public JwtProvider(JwTokenUtil jwTokenUtil, UserService userService) {
        this.jwTokenUtil = jwTokenUtil;
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    public String generateToken(Authentication authentication) {
        OAuth2User principal = (OAuth2User) authentication.getPrincipal();
        Long id = Long.parseLong(authentication.getName());
        String name = principal.getAttribute("login");

        User user = new User();
        user.setId(id);
        user.setUserName(name);

        UserDTO userDTO = userService.addUser(user);
        user.setRole(userDTO.getRole());

        String token = jwTokenUtil.generateToken(user);
        cache.put(token, authentication);
        return token;
    }

    public Authentication getAuth(String token) {
        return cache.getOrDefault(token, null);
    }
}

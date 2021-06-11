package com.revature.initiative.security;

import com.revature.initiative.dto.UserDTO;
import com.revature.initiative.enums.Role;
import com.revature.initiative.model.User;
import com.revature.initiative.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class JwtProvider {
    private final JwTokenUtil jwTokenUtil;
    private final Map<String, Authentication> cache = new HashMap<>();
    private final UserService userService;

    @Autowired
    public JwtProvider(JwTokenUtil jwTokenUtil, UserService userService) {
        this.jwTokenUtil = jwTokenUtil;
        this.userService = userService;
    }

    public String generateToken(Authentication authentication) {
        String token;

        OAuth2User principal = (OAuth2User) authentication.getPrincipal();
        Long id = Long.parseLong(authentication.getName());
        String name = principal.getAttribute("login");

        UserDTO storedUser = userService.findUserById(id);
        if (storedUser != null) {
            token = jwTokenUtil.generateToken(storedUser);
        } else {
            UserDTO user = new UserDTO();
            user.setId(id);
            user.setUsername(name);
            user.setRole(Role.USER);

            User somebody = userService.mapToUser(user);

            userService.addUser(somebody);

            token = jwTokenUtil.generateToken(user);
        }

        cache.put(token, authentication);
        return token;
    }

    public Authentication getAuth(String token) {
        return cache.getOrDefault(token, null);
    }
}

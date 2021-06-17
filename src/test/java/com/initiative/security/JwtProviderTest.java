package com.initiative.security;

import com.revature.initiative.dto.UserDTO;
import com.revature.initiative.enums.Role;
import com.revature.initiative.security.JwTokenUtil;
import com.revature.initiative.security.JwtProvider;
import com.revature.initiative.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;

public class JwtProviderTest {
    static private JwtProvider jwtProvider;
    static private UserService userService;
    static private JwTokenUtil jwTokenUtil;
    @BeforeAll
    static void setup(){
        userService = mock(UserService.class);
        jwTokenUtil = mock(JwTokenUtil.class);
        jwtProvider = new JwtProvider(jwTokenUtil,userService);
    }

    @Test
    void generateTokenTest(){
        String test = "test";

        UserDTO user= new UserDTO();
        user.setId(1l);
        user.setUsername("test");
        user.setRole(Role.ADMIN);
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("test",1l);
        OAuth2User oauthUser = new DefaultOAuth2User(null,userMap,"test");
        Authentication auth = new UsernamePasswordAuthenticationToken(oauthUser,user);
        Assertions.assertFalse(test.equals(jwtProvider.generateToken(auth)));
    }
    @Test
    void getTest(){
        String test = "test";
        Assertions.assertFalse(jwtProvider.getAuth(test) != null);
    }
}

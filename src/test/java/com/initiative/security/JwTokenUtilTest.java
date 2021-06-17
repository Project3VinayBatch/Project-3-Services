package com.initiative.security;

import com.revature.initiative.dto.UserDTO;
import com.revature.initiative.enums.Role;
import com.revature.initiative.model.User;
import com.revature.initiative.security.JwTokenUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.security.Key;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JwTokenUtilTest {

    private static JwTokenUtil jwTokenUtil;
    private static Jwts jwts;
    @BeforeAll
    static void setup(){
        jwTokenUtil = new JwTokenUtil();

        /*
        In order to mock this final class a file in: src/test/resources/mockito-extensions/org.mockito.plugins.MockMaker
        had to be made with "mock-maker-inline" as its content, if not Mockito will not mock final classes
         */
        jwts = mock(Jwts.class);
    }
    @Test
    void initTest(){
        JwTokenUtil jwTokenUtil2 = new JwTokenUtil();
        jwTokenUtil.init();
        jwTokenUtil2.init();
        Assertions.assertFalse(jwTokenUtil2.getSecretKey() == jwTokenUtil.getSecretKey());
    }

    @Test
    void validateTokenTest(){

        Role role = Role.ADMIN;
        UserDTO user = new UserDTO();
        user.setId(1l);
        user.setUsername("test");
        user.setRole(role);
        jwTokenUtil.init();
        jwTokenUtil.getSecretKey();
        Assertions.assertFalse(jwTokenUtil.validateToken(jwTokenUtil.generateToken(user),"1").equals(1009));
    }

    @Test
    void generateTokenTest(){
        UserDTO user = new UserDTO();
        Role role = Role.ADMIN;
        user.setId(1l);
        user.setUsername("test");
        user.setRole(role);
        jwTokenUtil.init();

        Assertions.assertFalse("test" == jwTokenUtil.generateToken(user));
    }

}

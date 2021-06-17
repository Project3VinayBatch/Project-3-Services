package com.initiative.security;

import com.revature.initiative.security.JwTokenUtil;
import com.revature.initiative.security.TokenFilter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TokenFilterTest{

    private static TokenFilter tokenFilter;
    private static JwTokenUtil jwTokenUtil;

    @BeforeAll
    static void setup(){
        jwTokenUtil = mock(JwTokenUtil.class);
        jwTokenUtil.init();
        tokenFilter = new TokenFilter(jwTokenUtil);
    }

//    @Test
//    void doFilterInternalTest() throws ServletException, IOException {
//        MockHttpServletResponse response = new MockHttpServletResponse();
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        MockFilterChain filterChain =new MockFilterChain();
//        JwTokenUtil jwt = new JwTokenUtil();
//        jwt.init();
//        TokenFilter tokenFilter1 = new TokenFilter(jwt);
//        JwtParser jwts = mock(JwtParser.class);
//        Jws<Claims> claimsJws = jwts.parseClaimsJws("test");
//        when(jwts.parseClaimsJws(anyString())).thenReturn(claimsJws);
//        request.addHeader("Authorization","test weuhiwe.ifbwqfkwfbwefkwehb.folqbhflwqf");
//
//        Assertions.assertThrows(NullPointerException.class,()->{
//            tokenFilter1.doFilterInternal(request,response, filterChain);
//        });
//    }

}

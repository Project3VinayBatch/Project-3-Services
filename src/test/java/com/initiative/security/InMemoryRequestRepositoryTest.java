package com.initiative.security;

import com.revature.initiative.security.InMemoryRequestRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

import javax.validation.constraints.Null;


public class InMemoryRequestRepositoryTest {

    private static InMemoryRequestRepository inMemoryRequestRepository;

    @BeforeAll
    static void setup(){
        inMemoryRequestRepository = new InMemoryRequestRepository();
    }

    @Test
    void loadAuthorizationRequestTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        //test if branch works correctly when given a null state parameter
        Assertions.assertTrue(null == inMemoryRequestRepository.loadAuthorizationRequest(request));
    }
    @Test
    void loadAuthorizationRequestTest2(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServerName("test");
        request.setParameter("state","active");
        //test if branch works correctly when given a state parameter
        Assertions.assertTrue(null == inMemoryRequestRepository.loadAuthorizationRequest(request));
    }
    @Test
    void saveAuthorizationRequestTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        //OAuth2AuthorizationRequest oauthRequest = new MockHttpServletRequest();

        Assertions.assertThrows(NullPointerException.class,()->{
            inMemoryRequestRepository.saveAuthorizationRequest(null,request,response);
        });
    }

    @Test
    void removeAuthorizationTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletRequest requestWithParam = new MockHttpServletRequest();
        requestWithParam.addParameter("state","test");
        Assertions.assertFalse(inMemoryRequestRepository.removeAuthorizationRequest(request) != null);
        Assertions.assertFalse(inMemoryRequestRepository.removeAuthorizationRequest(requestWithParam) != null);
    }
}

package com.initiative.security;

import com.revature.initiative.security.InMemoryRequestRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;



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
    }
}

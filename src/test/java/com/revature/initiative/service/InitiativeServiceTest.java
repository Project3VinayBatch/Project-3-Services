package com.revature.initiative.service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.revature.initiative.dto.FileDTO;
import com.revature.initiative.dto.InitiativeDTO;
import com.revature.initiative.enums.InitiativeState;
import com.revature.initiative.enums.Role;
import com.revature.initiative.exception.EmptyEntity;
import com.revature.initiative.model.Initiative;
import com.revature.initiative.model.User;
import com.revature.initiative.repository.InitiativeRepository;
import com.revature.initiative.repository.UserRepository;
import io.swagger.models.auth.In;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class InitiativeServiceTest {

    private InitiativeRepository initiativeRepository;
    private UserRepository userRepository;

    private InitiativeService testSubject;

    @BeforeEach
    void setUp() {
        initiativeRepository = mock(InitiativeRepository.class);
        userRepository = mock(UserRepository.class);
        testSubject = new InitiativeServiceImpl(initiativeRepository, userRepository);
    }

    private Initiative generateInit(int seed) {
        Initiative ret = new Initiative();

        ret.setFiles(new HashSet<>());
        ret.setState(InitiativeState.values()[seed % 2]);
        ret.setMembers(new HashSet<>());
        ret.setDescription("" + seed);
        ret.setTitle("" + seed);
        ret.setPointOfContactId((long) seed);
        ret.setCreatedById((long) seed);
        ret.setCreatedOn(Date.from(Instant.now()));
        ret.setId((long) seed);
        ret.setUpdatedAt(Date.from(Instant.now()));

        return ret;
    }

    private InitiativeDTO generateInitDTO(int seed) {
        InitiativeDTO ret = new InitiativeDTO();

        ret.setFiles(new HashSet<>());
        ret.setState(InitiativeState.values()[seed % 2]);
        ret.setMembers(new HashSet<>());
        ret.setDescription("" + seed);
        ret.setTitle("" + seed);
        ret.setInitiativeId((long) seed);
        ret.setPointOfContact((long) seed);
        ret.setCreatedBy((long) seed);

        return ret;
    }

    private User generateUser(int seed) {
        User ret = new User();

        ret.setFiles(new ArrayList<>());
        ret.setInitiatives(new HashSet<>());
        ret.setRole(Role.values()[seed % 2]);
        ret.setUsername("" + seed);
        ret.setId((long) seed);

        return ret;
    }

    @Test
    void addInitiativeRuns() {
        when(initiativeRepository.save(any(Initiative.class))).thenReturn(generateInit(1));
        InitiativeDTO test = testSubject.addInitiative(generateInitDTO(1));
        Assertions.assertEquals(1, test.getInitiativeId());
    }

    @Test
    void addInitiativeThrowsOnNull() {
        Assertions.assertThrows(Throwable.class, () -> {
            testSubject.addInitiative(null);
        });
    }

    @Test
    void addInitiativeRedirectsSQLError() {
        when(initiativeRepository.save(any(Initiative.class))).thenThrow(mock(org.springframework.dao.DataIntegrityViolationException.class));
        try {
            testSubject.addInitiative(generateInitDTO(1));
        } catch (Throwable e) {
            Assertions.assertNotEquals(org.springframework.dao.DataIntegrityViolationException.class, e.getClass());
            return;
        }
        Assertions.fail();
    }

    @Test
    void getInitiativeRuns() {
        when(initiativeRepository.findById(1l)).thenReturn(Optional.of(generateInit(1)));
        InitiativeDTO test = testSubject.getInitiative(1l);
        Assertions.assertEquals(1L, test.getInitiativeId());
    }

    @Test
    void getInitiativeThrowsWhenNoElement() {
        when(initiativeRepository.findById(1l)).thenReturn(Optional.empty());
        Assertions.assertThrows(Throwable.class, () ->
                testSubject.getInitiative(1L));
    }

    @Test
    void testGetInitiative() {
        when(initiativeRepository.findByTitle("1")).thenReturn(generateInit(1));
        InitiativeDTO test = testSubject.getInitiative("1");
        Assertions.assertEquals(1L, test.getInitiativeId());
    }

    @Test
    void getInitiatives() {
        List<Initiative> data = new ArrayList<>();
        data.add(generateInit(1));
        data.add(generateInit(2));
        data.add(generateInit(3));
        when(initiativeRepository.findAll()).thenReturn(data);
        List<InitiativeDTO> test = testSubject.getInitiatives();
        Assertions.assertEquals(3, test.size());
        Assertions.assertEquals(1l, test.get(0).getInitiativeId());
        Assertions.assertEquals(2l, test.get(1).getInitiativeId());
        Assertions.assertEquals(3l, test.get(2).getInitiativeId());
    }

    @Test
    void testGetInitiatives() {
        List<Initiative> data = new ArrayList<>();
        data.add(generateInit(1));
        data.add(generateInit(2));
        data.add(generateInit(3));
        for (Initiative i : data) i.setState(InitiativeState.values()[0]);
        when(initiativeRepository.findByState(InitiativeState.values()[0])).thenReturn(data);
        List<InitiativeDTO> test = testSubject.getInitiatives(InitiativeState.values()[0]);
        Assertions.assertEquals(3, test.size());
        Assertions.assertEquals(1l, test.get(0).getInitiativeId());
        Assertions.assertEquals(2l, test.get(1).getInitiativeId());
        Assertions.assertEquals(3l, test.get(2).getInitiativeId());
    }

    @Test
    void setInitiativeStateRuns() {
        when(initiativeRepository.findById(1L)).thenReturn(Optional.of(generateInit(1)));
        when(initiativeRepository.save(any(Initiative.class))).thenReturn(generateInit(1));
        testSubject.setInitiativeState(1l, InitiativeState.values()[2]);
        ArgumentCaptor<Initiative> captor = ArgumentCaptor.forClass(Initiative.class);
        verify(initiativeRepository).save(captor.capture());
        Assertions.assertEquals(InitiativeState.values()[2], captor.getValue().getState());
        Assertions.assertEquals(1, captor.getValue().getId());
    }

    @Test
    void setInitiativeStateThrowsOnNotFound() {
        when(initiativeRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(Throwable.class, () ->
                testSubject.setInitiativeState(1l, InitiativeState.values()[2]));
    }

    @Test
    void setInitiativePOCRuns() {
        when(initiativeRepository.findByTitle(anyString())).thenReturn(generateInit(1));
        when(userRepository.findUserById(anyLong())).thenReturn(generateUser(1));
        testSubject.setInitiativePOC("toasters", 10l);
        ArgumentCaptor<Initiative> captor = ArgumentCaptor.forClass(Initiative.class);
        verify(initiativeRepository).save(captor.capture());
        Assertions.assertEquals(10l, captor.getValue().getPointOfContactId());
        Assertions.assertEquals(1, captor.getValue().getId());
    }

    @Test
    void setInitiativePOCThrowsOnNullInitiative() {
        when(initiativeRepository.findByTitle(anyString())).thenReturn(null);
        when(userRepository.findUserById(anyLong())).thenReturn(generateUser(1));
        Assertions.assertThrows(Throwable.class, () ->
                testSubject.setInitiativePOC("toasters", 10l));
    }

    @Test
    void setInitiativePOCThrowsOnNullUser() {
        when(initiativeRepository.findByTitle(anyString())).thenReturn(generateInit(1));
        when(userRepository.findUserById(anyLong())).thenReturn(null);
        Assertions.assertThrows(Throwable.class, () ->
                testSubject.setInitiativePOC("toasters", 10l));
    }

    @Test
    void remInitiative() {
        testSubject.remInitiative(1l);
        verify(initiativeRepository, times(1)).deleteById(1l);
    }

    @Test
    void testRemInitiative() {
        testSubject.remInitiative("toasters");
        verify(initiativeRepository, times(1)).deleteByTitle("toasters");
    }
}
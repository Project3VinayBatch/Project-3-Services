package com.revature.initiative.service;

import com.revature.initiative.dto.InitiativeDTO;
import com.revature.initiative.enums.InitiativeState;

import java.util.List;

public interface InitiativeService {
    InitiativeDTO addInitiative(InitiativeDTO init);

    InitiativeDTO getInitiative(long id);

    InitiativeDTO getInitiative(String title);

    List<InitiativeDTO> getInitiatives();

    List<InitiativeDTO> getInitiatives(InitiativeState state);

    InitiativeDTO setInitiativeState(Long id, InitiativeState state);

    InitiativeDTO setInitiativePOC(long initId, long userId);

    InitiativeDTO setInitiativePOC(long initId, String username);

    InitiativeDTO setInitiativePOC(String title, long userId);

    InitiativeDTO setInitiativePOC(String title, String username);

    void remInitiative(long id);

    void remInitiative(String title);
}

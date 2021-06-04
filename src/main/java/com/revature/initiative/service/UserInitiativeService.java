package com.revature.initiative.service;

public interface UserInitiativeService {
    void signUp(Long userId, Long initiativeId);

    void remove(long userId, long initiativeId);
}

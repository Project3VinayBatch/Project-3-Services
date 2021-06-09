package com.revature.initiative.service;

import com.revature.initiative.exception.DuplicateEntity;

public interface UserInitiativeService {
    void signUp(Long userId, Long initiativeId) throws DuplicateEntity;

    void remove(long userId, long initiativeId);
}

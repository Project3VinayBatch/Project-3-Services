package com.revature.initiative.service;

import com.revature.initiative.exception.DuplicateEntity;
import com.revature.initiative.model.UserInitiative;
import com.revature.initiative.repository.UserInitiativesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInitiativeServiceImpl implements UserInitiativeService {

    private UserInitiativesRepository userInitiativesRepository;

    @Autowired
    public UserInitiativeServiceImpl(UserInitiativesRepository userInitiativesRepository) {
        this.userInitiativesRepository = userInitiativesRepository;
    }

    @Override
    public void signUp(Long userId, Long initiativeId) throws DuplicateEntity {
        if (userInitiativesRepository.findByinitiativeIdAndUserId(initiativeId, userId) == null) {
            userInitiativesRepository.save(new UserInitiative(initiativeId, userId));
        } else {
            throw new DuplicateEntity();
        }
    }

    @Override
    public void remove(long userId, long initiativeId) {
        userInitiativesRepository.deleteByinitiativeIdAndUserId(userId, initiativeId);
    }
}

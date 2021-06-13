package com.revature.initiative.service;

import com.revature.initiative.exception.DuplicateEntity;
import com.revature.initiative.model.UserInitiative;
import com.revature.initiative.repository.UserInitiativesRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInitiativeServiceImpl implements UserInitiativeService {

    private final UserInitiativesRepository userInitiativesRepository;
    private static final Logger logger = LogManager.getLogger(UserInitiativeServiceImpl.class);

    @Autowired
    public UserInitiativeServiceImpl(UserInitiativesRepository userInitiativesRepository) {
        this.userInitiativesRepository = userInitiativesRepository;
    }

    @Override
    public void signUp(Long userId, Long initiativeId) throws DuplicateEntity {
        if (userInitiativesRepository.findByinitiativeIdAndUserId(initiativeId, userId) == null) {
            userInitiativesRepository.save(new UserInitiative(initiativeId, userId));
            logger.info("User signed up with [ UserID: {}, InitiativeID: {} ]", userId, initiativeId);
        } else {
            logger.warn("ERROR: User already signed up with [ UserID: {}, InitiativeID: {} ]", userId, initiativeId);
            throw new DuplicateEntity();
        }
    }

    @Override
    public void remove(long userId, long initiativeId) {
        UserInitiative userInit = userInitiativesRepository.findByinitiativeIdAndUserId(initiativeId, userId);
        if (userInit != null) {
            userInitiativesRepository.deleteById(userInit.getId());
            logger.info("User removed with [ UserID: {}, InitiativeID: {} ]", userId, initiativeId);
        }
    }
}

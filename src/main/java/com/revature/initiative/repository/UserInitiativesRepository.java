package com.revature.initiative.repository;

import com.revature.initiative.model.UserInitiative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInitiativesRepository extends JpaRepository<UserInitiative, Long> {
    UserInitiative findByinitiativeIdAndUserId(Long initiativeId, Long userId);
}

package com.revature.initiative.repository;

import com.revature.initiative.enums.InitiativeState;
import com.revature.initiative.model.Initiative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InitiativeRepository extends JpaRepository<Initiative, Long> {
    List<Initiative> findByState(InitiativeState state);
    Initiative findByTitle(String title);
    void deleteByTitle(String title);
}

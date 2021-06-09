package com.revature.initiative.repository;

import com.revature.initiative.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByuserName(String username);

    User findUserById(Long userId);
}

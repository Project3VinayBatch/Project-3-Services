package com.revature.initiative.repo;

import com.revature.initiative.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByuserName(String username);
}

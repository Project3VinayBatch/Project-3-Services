package com.revature.initiative.repository;
import com.revature.initiative.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findUsersByUserName(String username);
    User findByuserName(String username);

}

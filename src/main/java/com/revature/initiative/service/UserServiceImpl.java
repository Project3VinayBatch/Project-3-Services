package com.revature.initiative.service;

import com.revature.initiative.dto.UserDTO;
import com.revature.initiative.enums.Role;
import com.revature.initiative.model.User;
import com.revature.initiative.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO getUser(Long userId) {
        return mapTokenUserDTO(userRepository.findUserById(userId));
    }

    /**
     * One of our programmers is an idiot and called everything ret. I am so sorry.
     * EDIT: Not sorry.
     * @param userId the Id of the user you are trying to find
     * @return  the UserDTO if they are found in the database
     */
    @Override
    public UserDTO findUserById(Long userId) {

        logger.info("User retrieved with [ UserID: {} ]", userId);

        return mapTokenUserDTO(userRepository.findUserById(userId));
    }

    @Override
    public UserDTO findUserByUsername(String username) {
        logger.info("User retrieved with [ Username: {} ]", username);
        return mapTokenUserDTO(userRepository.findByUsername(username));
    }

    @Override
    public UserDTO addUser(User user) {
        //UserDTO ret = mapTokenUserDTO(user);
        logger.info("User added with [ UserID: {} ]", user.getId());
        //if (ret == null) {
        return mapTokenUserDTO(userRepository.save(user));
        //} else {
        //    return ret;
        //}
    }

    /**
     * One of our programmers is really terrible and called user ent for some reason. Again, I am very very sorry
     * EDIT: Still not sorry.
     * Map a user to a userDTO to use with JWT
     * @param ent ??? Short for entity? Why wouldn't they just say entity? Anyways this is the user
     * @return the UserDTO version of the User
     */
    @Override
    public UserDTO mapTokenUserDTO(User ent) {
        if (ent == null) return null;
        UserDTO ret = new UserDTO();

        ret.setId(ent.getId());
        ret.setUsername(ent.getUsername());

        if (ent.getRole() == null) {
            ret.setRole(Role.USER);
        } else {
            ret.setRole(ent.getRole());
        }

        return ret;
    }

    @Override
    public User mapToUser(UserDTO treeBeard) {
        if (treeBeard == null) return null;
        User ret = new User();

        ret.setId(treeBeard.getId());
        ret.setUsername(treeBeard.getUsername());

        if (treeBeard.getRole() == null) {
            ret.setRole(Role.USER);
        } else {
            ret.setRole(treeBeard.getRole());
        }

        return ret;
    }
}
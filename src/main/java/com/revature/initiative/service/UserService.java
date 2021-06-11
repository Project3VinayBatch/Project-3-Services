package com.revature.initiative.service;

import com.revature.initiative.dto.UserDTO;
import com.revature.initiative.model.User;

public interface UserService {
    UserDTO getUser(Long userId);
    UserDTO findUserById(Long userId);
    UserDTO findUserByUsername(String username);

    UserDTO addUser(User user);

    UserDTO mapTokenUserDTO(User ent);
    User mapToUser(UserDTO treeBeard);
}

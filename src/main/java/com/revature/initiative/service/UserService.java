package com.revature.initiative.service;

import com.revature.initiative.dto.UserDTO;
import com.revature.initiative.model.User;

public interface UserService {
    UserDTO getUser(Long userId);
    UserDTO addUser(User user);
}

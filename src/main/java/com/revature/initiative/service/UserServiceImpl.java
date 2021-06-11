package com.revature.initiative.service;

import com.revature.initiative.dto.UserDTO;
import com.revature.initiative.enums.Role;
import com.revature.initiative.model.User;
import com.revature.initiative.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO getUser(Long userId) {
        return mapTokenUserDTO(userRepository.findUserById(userId));
    }

    @Override
    public UserDTO addUser(User user) {
        UserDTO ret = mapTokenUserDTO(userRepository.findUserById(user.getId()));

        if (ret == null) {
            return mapTokenUserDTO(userRepository.save(user));
        } else {
            return ret;
        }
    }

    //map an user to a userDTO to use with JWT
    private UserDTO mapTokenUserDTO(User ent) {
        if (ent == null) return null;
        UserDTO ret = new UserDTO();

        ret.setId(ent.getId());
        ret.setUserName(ent.getUserName());

        if (ent.getRole() == null) {
            ret.setRole(Role.USER);
        } else {
            ret.setRole(ent.getRole());
        }

        return ret;
    }
}

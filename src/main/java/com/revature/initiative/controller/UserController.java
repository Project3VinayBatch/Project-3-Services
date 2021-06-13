package com.revature.initiative.controller;

import com.revature.initiative.dto.UserDTO;
import com.revature.initiative.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public ResponseEntity<UserDTO> getLoggedInUser(@AuthenticationPrincipal Authentication user) {
        UserDTO ret = userService.findUserByUsername((String) user.getPrincipal());
        return ResponseEntity.ok(ret);
    }
}

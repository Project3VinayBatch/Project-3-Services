package com.revature.initiative.controller;

import com.revature.initiative.dto.InitiativeDTO;
import com.revature.initiative.exception.InvalidTitleException;
import com.revature.initiative.exception.UserNotFoundException;
import com.revature.initiative.exception.DuplicateEntity;
import com.revature.initiative.service.InitiativeService;
import com.revature.initiative.service.UserInitiativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class InitiativeController {

    private InitiativeService initiativeService;
    private UserInitiativeService userInitiativeService;

    @Autowired
    public InitiativeController(InitiativeService initiativeService, UserInitiativeService userInitiativeService) {
        this.initiativeService = initiativeService;
        this.userInitiativeService = userInitiativeService;
    }

    @PostMapping("initiative")
    public ResponseEntity<Object> createInitiative(@RequestBody InitiativeDTO initiativeDTO) {
        try {
            return ResponseEntity.ok(initiativeService.addInitiative(initiativeDTO));
        } catch (InvalidTitleException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @GetMapping("initiatives")
    public ResponseEntity<List<InitiativeDTO>> getAllInitiatives() {
        return ResponseEntity.ok(initiativeService.getInitiatives());
    }

    @PatchMapping("updatepoc")
    public ResponseEntity<Object> updateInitiativePOC(@RequestBody InitiativeDTO initiativeDTO) {
        try {
            return ResponseEntity.ok(initiativeService.setInitiativePOC(initiativeDTO.getTitle(), initiativeDTO.getPointOfContact()));
        } catch (UserNotFoundException | InvalidTitleException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @PostMapping("initiative/signup/{userId}/{initiativeId}")
    public ResponseEntity<Object> signUp(long userId, long initiativeId){
        try {
            userInitiativeService.signUp(userId, initiativeId);
        } catch (DuplicateEntity duplicateEntity) {
            return ResponseEntity.badRequest()
                    .body("ERROR: relation already exists");
        }
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("initiative/signup/{userId}/{initiativeId}")
    public ResponseEntity<Object> signOff(long userId, long initiativeId){
        userInitiativeService.remove(userId, initiativeId);
        return ResponseEntity.ok(null);
    }
}

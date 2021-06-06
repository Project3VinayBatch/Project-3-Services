package com.revature.initiative.controller;

import com.revature.initiative.dto.InitiativeDTO;
import com.revature.initiative.exceptions.DuplicateEntity;
import com.revature.initiative.model.UserInitiative;
import com.revature.initiative.service.InitiativeService;
import com.revature.initiative.service.UserInitiativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
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
    public ResponseEntity<InitiativeDTO> createInitiative(@RequestBody InitiativeDTO initiativeDTO) {
        return ResponseEntity.ok(initiativeService.addInitiative(initiativeDTO));
    }

    @GetMapping("initiatives")
    public ResponseEntity<List<InitiativeDTO>> getAllInitiatives() {
        return ResponseEntity.ok(initiativeService.getInitiatives());
    }

    @PatchMapping("updatepoc")
    public ResponseEntity<InitiativeDTO> updateInitiativePOC(@RequestBody InitiativeDTO initiativeDTO) {
        return ResponseEntity.ok(initiativeService.setInitiativePOC(initiativeDTO.getTitle(), initiativeDTO.getPointOfContact()));
    }

    @PostMapping("initiative/signup/{userId}/{initiativeId}")
    public ResponseEntity signUp(long userId, long initiativeId){
        try {
            userInitiativeService.signUp(userId, initiativeId);
        } catch (DuplicateEntity duplicateEntity) {
            ResponseEntity.BodyBuilder ret = ResponseEntity.badRequest();
            ret.body("ERROR: relation already exists");
        }
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("initiative/signup/{userId}/{initiativeId}")
    public ResponseEntity signOff(long userId, long initiativeId){
        userInitiativeService.remove(userId, initiativeId);
        return ResponseEntity.ok(null);
    }
}

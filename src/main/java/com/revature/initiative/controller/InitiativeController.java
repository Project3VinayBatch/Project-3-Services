package com.revature.initiative.controller;

import com.revature.initiative.dto.InitiativeDTO;
import com.revature.initiative.exception.DuplicateEntity;
import com.revature.initiative.service.InitiativeService;
import com.revature.initiative.service.UserInitiativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InitiativeController {

    private final InitiativeService initiativeService;
    private final UserInitiativeService userInitiativeService;

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
    public ResponseEntity<Object> signUp(@PathVariable Long userId, @PathVariable Long initiativeId) {
        try {
            userInitiativeService.signUp(userId, initiativeId);
        } catch (DuplicateEntity duplicateEntity) {
            return ResponseEntity.badRequest()
                    .body("ERROR: relation already exists");
        }
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("initiative/signup/{userId}/{initiativeId}")
    public ResponseEntity<Object> signOff(@PathVariable Long userId, @PathVariable Long initiativeId) {
        userInitiativeService.remove(userId, initiativeId);
        return ResponseEntity.ok(null);
    }
}

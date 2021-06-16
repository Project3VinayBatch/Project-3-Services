package com.revature.initiative.controller;

import com.revature.initiative.dto.InitiativeDTO;
import com.revature.initiative.enums.InitiativeState;
import com.revature.initiative.exception.InvalidTitleException;
import com.revature.initiative.exception.UserNotFoundException;
import com.revature.initiative.exception.DuplicateEntity;
import com.revature.initiative.service.InitiativeService;
import com.revature.initiative.service.UserInitiativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
public class InitiativeController {

    private final InitiativeService initiativeService;
    private final UserInitiativeService userInitiativeService;

    @Autowired
    public InitiativeController(InitiativeService initiativeService, UserInitiativeService userInitiativeService) {
        this.initiativeService = initiativeService;
        this.userInitiativeService = userInitiativeService;
    }

    @PostMapping("initiative")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
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

    @GetMapping("initiatives/id/{id}")
    public ResponseEntity<InitiativeDTO> getInitiativeById(@PathVariable Long id) {
        return ResponseEntity.ok(initiativeService.getInitiative(id));
    }



    @GetMapping("initiatives/{state}")
    public ResponseEntity<List<InitiativeDTO>> getInitiatives(@PathVariable InitiativeState state) {
        return ResponseEntity.ok(initiativeService.getInitiatives(state));
    }

    @PostMapping("initiative/{id}/{state}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<InitiativeDTO> setInitiativeState(@PathVariable Long id, @PathVariable InitiativeState state) {
        return ResponseEntity.ok(initiativeService.setInitiativeState(id, state));
    }

    @PatchMapping("updatepoc")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Object> updateInitiativePOC(@RequestBody InitiativeDTO initiativeDTO) {
        try {
            return ResponseEntity.ok(initiativeService.setInitiativePOC(initiativeDTO.getTitle(), initiativeDTO.getPointOfContact()));
        } catch (UserNotFoundException | InvalidTitleException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
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

package com.revature.initiative.controller;

import com.revature.initiative.dto.InitiativeDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InitiativeController {

    @PostMapping("initiative")
    public ResponseEntity<InitiativeDTO> createInitiative(@RequestBody InitiativeDTO initiativeDTO) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("initiatives")
    public ResponseEntity<List<InitiativeDTO>> getAllInitiatives() {
        return new ResponseEntity<>(HttpStatus.OK);
    }


}

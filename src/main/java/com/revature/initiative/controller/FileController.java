package com.revature.initiative.controller;

import com.revature.initiative.dto.FileDTO;
import com.revature.initiative.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("files")
    public ResponseEntity<List<FileDTO>> getAllInitiatives() {
        return ResponseEntity.ok(fileService.getFiles());
    }

    @GetMapping("files/byusername/{username}")
    public ResponseEntity<List<FileDTO>> getAllInitiatives(@PathVariable String username) {
        return ResponseEntity.ok(fileService.getFilesByUsername(username));
    }

    @GetMapping("files/by-initiative-id/{initiativeId}")
    public ResponseEntity<List<FileDTO>> getAllInitiatives(@PathVariable Long initiativeId) {
        return ResponseEntity.ok(fileService.getFilesByInitiativeId(initiativeId));
    }
}

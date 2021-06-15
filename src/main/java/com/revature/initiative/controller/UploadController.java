package com.revature.initiative.controller;

import com.revature.initiative.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://localhost:4200")
@Controller
public class UploadController {
    private final UploadService amazonS3BucketService;

    @Autowired
    public UploadController(UploadService amazonS3BucketService) {
        this.amazonS3BucketService = amazonS3BucketService;
    }

    @PostMapping(value = "/uploadFile/{username}/{id}")
    public ResponseEntity<String> uploadFile(@RequestPart MultipartFile file, @PathVariable String username, @PathVariable Long id) {
        String output = this.amazonS3BucketService.uploadFile(file, username, id);
        return ResponseEntity.ok(output);
    }
}
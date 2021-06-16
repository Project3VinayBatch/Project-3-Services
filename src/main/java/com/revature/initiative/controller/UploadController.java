package com.revature.initiative.controller;

import com.revature.initiative.service.UploadService;
import com.sun.org.apache.xpath.internal.operations.Mult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

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
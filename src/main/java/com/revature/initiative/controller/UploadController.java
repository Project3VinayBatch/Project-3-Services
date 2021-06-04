package com.revature.initiative.controller;

import com.revature.initiative.dto.FileDTO;
import com.revature.initiative.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
        import org.springframework.web.bind.annotation.*;
        import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

@CrossOrigin(origins = "http://localhost:4200")
@Controller
public class UploadController {

    private UploadService amazonS3BucketService;

    @Autowired
    public UploadController(UploadService amazonS3BucketService) {
        this.amazonS3BucketService = amazonS3BucketService;
    }

    @PostMapping(value = "/uploadFile/{username}/{id}" )
    public ResponseEntity uploadFile(@RequestPart MultipartFile file, @PathVariable String username, @PathVariable String id) {
        Long initiativeId = Long.parseLong(id);
        String output = this.amazonS3BucketService.uploadFile(file,username,initiativeId);
        return ResponseEntity.ok(output);
    }
}
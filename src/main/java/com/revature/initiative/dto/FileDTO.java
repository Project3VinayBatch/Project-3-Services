package com.revature.initiative.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileDTO {
    private String username;
    private MultipartFile file;
    private Long initiativeId;
}

package com.revature.initiative.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileDTO {
    private Long id;
    private String fileName;
    private String fileUrl;
    private Long uploadedBy;
    private Long initiativeId;
}

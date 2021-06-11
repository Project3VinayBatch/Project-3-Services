package com.revature.initiative.dto;

import lombok.Data;

@Data
public class FileDTO {
    private Long id;
    private String fileName;
    private String fileUrl;
    private Long uploadedBy;
    private Long initiativeId;
}

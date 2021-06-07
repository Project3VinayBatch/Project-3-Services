package com.revature.initiative.service;
import org.springframework.web.multipart.MultipartFile;
public interface UploadService {
    String uploadFile(MultipartFile multipartFile, String username, Long initiativeId);
    void uploadFileURLtoDB(String fileURL, String filename,  String username, Long initiativeId);
}

package com.revature.initiative.service;

import com.revature.initiative.dto.FileDTO;

import java.util.List;

public interface FileService {

    List<FileDTO> getFiles();
    List<FileDTO> getFilesByUsername(String userName);
    List<FileDTO> getFilesByInitiativeId(Long initiativeId);



}

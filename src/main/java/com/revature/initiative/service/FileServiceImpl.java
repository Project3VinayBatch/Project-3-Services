package com.revature.initiative.service;
import com.revature.initiative.dto.FileDTO;
import com.revature.initiative.exception.UserException;
import com.revature.initiative.model.File;
import com.revature.initiative.model.User;
import com.revature.initiative.repository.FileRepository;
import com.revature.initiative.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    FileRepository fileRepository;
    UserRepository userRepository;

    @Autowired
    public FileServiceImpl(FileRepository fileRepository, UserRepository userRepository) {
        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
    }

    private static FileDTO fileMapDTO(File ent) {
        if (ent == null) return null;
        FileDTO ret = new FileDTO();
        ret.setId(ent.getId());
        ret.setFileName(ent.getFileName());
        ret.setFileUrl(ent.getFileURL());
        ret.setUploadedBy(ent.getUploadedById());
        ret.setInitiativeId(ent.getFileInitiativeId());
        return ret;
    }

    private static List<FileDTO> fileMapDTO(List<File> ent) {
        List<FileDTO> ret = new ArrayList<>();
        for (File i : ent) ret.add(fileMapDTO(i));
        return ret;
    }

    @Override
    public List<FileDTO> getFiles() {
        return fileMapDTO(fileRepository.findAll());
    }

    @Override
    public List<FileDTO> getFilesByUsername(String userName) {
        try {
            User user = userRepository.findByuserName(userName);
            return fileMapDTO(fileRepository.findAllByUploadedById(user.getId()));
        } catch (UserException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public List<FileDTO> getFilesByInitiativeId(Long initiativeId) {
        return fileMapDTO(fileRepository.findAllByFileInitiativeId(initiativeId));
    }
}

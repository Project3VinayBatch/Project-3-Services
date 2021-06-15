package com.revature.initiative.service;

import com.revature.initiative.repository.FileRepository;
import com.revature.initiative.repository.InitiativeRepository;
import com.revature.initiative.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;

import static org.mockito.Mockito.mock;

public class UploadServiceTesting {

    private UploadService uploadService;
    private UserRepository userRepository;
    private FileRepository fileRepository;
    private InitiativeRepository initiativeRepository;
    @BeforeAll
    void setup(){
        userRepository = mock(UserRepository.class);
        fileRepository = mock(FileRepository.class);
        initiativeRepository = mock(InitiativeRepository.class);
       // uploadService = new UploadServiceImpl();
    }
}

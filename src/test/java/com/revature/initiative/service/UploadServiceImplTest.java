package com.revature.initiative.service;

import com.amazonaws.services.s3.AmazonS3;
import com.revature.initiative.dto.InitiativeDTO;
import com.revature.initiative.enums.InitiativeState;
import com.revature.initiative.enums.Role;
import com.revature.initiative.exception.FileException;
import com.revature.initiative.model.File;
import com.revature.initiative.model.Initiative;
import com.revature.initiative.model.User;
import com.revature.initiative.repository.FileRepository;
import com.revature.initiative.repository.InitiativeRepository;
import com.revature.initiative.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.Times;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UploadServiceImplTest {

    private InitiativeRepository initiativeRepository;
    private FileRepository fileRepository;
    private UserRepository userRepository;
    private AmazonS3 amazonS3;

    private UploadService testSubject;

    @BeforeEach
    void setUp() {
        initiativeRepository = mock(InitiativeRepository.class);
        userRepository = mock(UserRepository.class);
        fileRepository = mock(FileRepository.class);
        amazonS3 = mock(AmazonS3.class);

        S3Provider provider = mock(S3Provider.class);
        when(provider.provide()).thenReturn(amazonS3);
        when(provider.getBucket()).thenReturn("testBucket");

        testSubject = new UploadServiceImpl(userRepository, fileRepository, initiativeRepository, provider);
    }

    private Initiative generateInit(int seed) {
        Initiative ret = new Initiative();

        ret.setFiles(new HashSet<>());
        ret.setState(InitiativeState.values()[seed % 2]);
        ret.setMembers(new HashSet<>());
        ret.setDescription("" + seed);
        ret.setTitle("" + seed);
        ret.setPointOfContactId((long) seed);
        ret.setCreatedById((long) seed);
        ret.setCreatedOn(Date.from(Instant.now()));
        ret.setId((long) seed);
        ret.setUpdatedAt(Date.from(Instant.now()));

        return ret;
    }

    private InitiativeDTO generateInitDTO(int seed) {
        InitiativeDTO ret = new InitiativeDTO();

        ret.setFiles(new HashSet<>());
        ret.setState(InitiativeState.values()[seed % 2]);
        ret.setMembers(new HashSet<>());
        ret.setDescription("" + seed);
        ret.setTitle("" + seed);
        ret.setInitiativeId((long) seed);
        ret.setPointOfContact((long) seed);
        ret.setCreatedBy((long) seed);

        return ret;
    }

    private User generateUser(int seed) {
        User ret = new User();

        ret.setFiles(new ArrayList<>());
        ret.setInitiatives(new HashSet<>());
        ret.setRole(Role.values()[seed % 2]);
        ret.setUsername("" + seed);
        ret.setId((long) seed);

        return ret;
    }

    private MultipartFile mockFile(){
        return new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );
    }

    @Test
    void uploadFileRuns() throws IOException {
        MultipartFile inputFile = mockFile();
        when(userRepository.findByUsername("timmy")).thenReturn(generateUser(1));
        when(initiativeRepository.findById(1l)).thenReturn(Optional.of(generateInit(1)));

        Assertions.assertEquals(
                "https://s3.cloudLocation.amazonaws.com/testBucket/hello.txt",
                testSubject.uploadFile(inputFile, "timmy", 1l));

        verify(fileRepository, times(1)).save(any(File.class));
        verify(amazonS3, times(1)).putObject(any());
    }

    @Test
    void uploadFileExitsOnFileReadException() throws IOException {
        MockMultipartFile inputFile = mock(MockMultipartFile.class);
        when(inputFile.getOriginalFilename()).thenReturn("");
        when(inputFile.getBytes()).thenThrow(new IOException());
        when(userRepository.findByUsername("timmy")).thenReturn(generateUser(1));
        when(initiativeRepository.findById(1l)).thenReturn(Optional.of(generateInit(1)));

        Assertions.assertNotEquals(
                "https://s3.cloudLocation.amazonaws.com/testBucket/hello.txt",
                testSubject.uploadFile(inputFile, "timmy", 1l));

        verify(fileRepository, times(0)).save(any(File.class));
        verify(amazonS3, times(0)).putObject(any());
    }

    @Test
    void uploadFileExitsOnUserNotExist() throws IOException {
        MultipartFile inputFile = mockFile();
        when(userRepository.findByUsername("timmy")).thenReturn(generateUser(1));
        when(initiativeRepository.findById(1l)).thenReturn(Optional.empty());

        Assertions.assertNotEquals(
                "https://s3.cloudLocation.amazonaws.com/testBucket/hello.txt",
                testSubject.uploadFile(inputFile, "timmy", 1l));

        verify(fileRepository, times(0)).save(any(File.class));
        verify(amazonS3, times(0)).putObject(any());
    }

    @Test
    void uploadFileExitsOnInitiativeNotExist() throws IOException {
        MultipartFile inputFile = mockFile();
        when(userRepository.findByUsername("timmy")).thenReturn(null);
        when(initiativeRepository.findById(1l)).thenReturn(Optional.of(generateInit(1)));

        Assertions.assertNotEquals(
                "https://s3.cloudLocation.amazonaws.com/testBucket/hello.txt",
                testSubject.uploadFile(inputFile, "timmy", 1l));

        verify(fileRepository, times(0)).save(any(File.class));
        verify(amazonS3, times(0)).putObject(any());
    }

    @Test
    void uploadFileExitsOnFileAlreadyExists() throws IOException {
        MultipartFile inputFile = mockFile();
        when(userRepository.findByUsername("timmy")).thenReturn(generateUser(1));
        when(initiativeRepository.findById(1l)).thenReturn(Optional.of(generateInit(1)));
        when(fileRepository.findFileByFileURLAndInitiativeIdAndUploadedBy(any(), any(), any()))
                .thenReturn(mock(File.class));

        Assertions.assertNotEquals(
                "https://s3.cloudLocation.amazonaws.com/testBucket/hello.txt",
                testSubject.uploadFile(inputFile, "timmy", 1l));

        verify(fileRepository, times(0)).save(any(File.class));
        verify(amazonS3, times(0)).putObject(any());
    }

    @Test
    void uploadFileURLtoDB() {
    }
}
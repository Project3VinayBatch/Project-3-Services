package com.revature.initiative.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.revature.initiative.exception.FileException;
import com.revature.initiative.exception.InitiativeException;
import com.revature.initiative.exception.UserException;
import com.revature.initiative.model.Initiative;
import com.revature.initiative.model.User;
import com.revature.initiative.repository.FileRepository;
import com.revature.initiative.repository.InitiativeRepository;
import com.revature.initiative.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

@Service
public class UploadServiceImpl implements UploadService {

    private AmazonS3 amazonS3;
    private final UserRepository userRepository;
    private final FileRepository fileRepository;
    private final InitiativeRepository initiativeRepository;
    private static final Logger logger = LogManager.getLogger(UploadServiceImpl.class);

    @Autowired
    public UploadServiceImpl(UserRepository userRepository, FileRepository fileRepository, InitiativeRepository initiativeRepository) {
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
        this.initiativeRepository = initiativeRepository;

    }

    @Value("${bucketName}")
    private String bucketName;
    @Value("${accessKey}")
    private String accessKey;
    @Value("${secretKey}")
    private String secretKey;
    @Value("${aws-region}")
    private String awsRegion;

    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(awsRegion)
                .build();
    }

    @Override
    public String uploadFile(MultipartFile multipartFile, String username, Long initiativeId) {
        String fileURL = "";
        try {
            File file = convertMultipartFileToFile(multipartFile);
            String fileName = multipartFile.getOriginalFilename();
            String endpointUrl = "https://s3.cloudLocation.amazonaws.com";
            fileURL = endpointUrl + "/" + bucketName + "/" + fileName;
            uploadFileURLtoDB(fileURL, fileName, username, initiativeId);
            uploadFileToBucket(fileName, file);
            Files.delete(file.toPath());
        } catch (Exception e) {
            logger.warn("ERROR: File not uploaded with information [ Username: {}, Initiative ID: {} ]", username, initiativeId);
            e.printStackTrace();
        }
        logger.info("File successfully uploaded with information [ Username: {}, Initiative ID: {} ]", username, initiativeId);
        return fileURL;
    }

    private File convertMultipartFileToFile(MultipartFile file) {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            logger.warn("ERROR: multipart file not converted");
            throw new FileException("Error when converting multipart file");
        }
        logger.info("Multipart file successfully converted");
        return convertedFile;
    }

    private void uploadFileToBucket(String fileName, File file) {
        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        logger.info("File successfully uploaded with [ Filename: {} ]", fileName);
    }

    @Override
    public void uploadFileURLtoDB(String fileURL, String fileName, String username, Long initiativeId) {
        try {
            User user = userRepository.findByuserName(username);
            if (user == null) throw new UserException("Username is not found");
            Initiative initiative = initiativeRepository.findById(initiativeId).orElseThrow(() -> new InitiativeException("Initiative not found"));
            com.revature.initiative.model.File file = new com.revature.initiative.model.File();
            com.revature.initiative.model.File fileTemp = fileRepository.findFileByFileURLAndInitiativeIdAndUploadedBy(fileURL, initiative, user);
            if (fileTemp == null) {
                file.setFileURL(fileURL);
                file.setFileName(fileName);
                file.setFileInitiativeId(initiative.getId());
                file.setUploadedById(user.getId());
                fileRepository.save(file);
            } else {
                logger.warn("File already exists in database with: [ File URL: {} ]", fileURL);
                throw new FileException("FileURL already exists the database");
            }
        } catch (NullPointerException e) {
            logger.warn("NullPointerException in uploadFileURLtoDB");
            e.printStackTrace();
        } catch (FileException f) {
            logger.warn("FileException in uploadFileURLtoDB");
            throw f;
        }
        logger.info("File successfully added to database with information [ File URL: {}, File Name: {}, Username: {}, Initiative ID: {} ]", fileURL, fileName, username, initiativeId);
    }
}

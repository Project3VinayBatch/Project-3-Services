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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class UploadServiceImpl implements UploadService {

    private AmazonS3 amazonS3;
    private UserRepository userRepository;
    private FileRepository fileRepository;
    private InitiativeRepository initiativeRepository;

    @Autowired
    public UploadServiceImpl(UserRepository userRepository, FileRepository fileRepository, InitiativeRepository initiativeRepository) {
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
        this.initiativeRepository = initiativeRepository;

    }

    private String endpointUrl = "https://s3.cloudLocation.amazonaws.com";
    @Value("${bucketName}")
    private String bucketName;
    @Value("${accessKey}")
    private String accessKey;
    @Value("${secretKey}")
    private String secretKey;
    @Value("${aws-region}")
    private String AwsRegion;

    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(AwsRegion)
                .build();
    }

    @Override
    public String uploadFile(MultipartFile multipartFile,String username, Long initiativeId) {
        String fileURL = "";
        try {
            File file = convertMultipartFileToFile(multipartFile);
            String fileName = multipartFile.getOriginalFilename();
            fileURL = endpointUrl + "/" + bucketName + "/" + fileName;
            uploadFileURLtoDB(fileURL,fileName, username, initiativeId);
            //uploadFileToBucket(fileName, file);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileURL;
    }

    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
        return convertedFile;
    }

    private void uploadFileToBucket(String fileName, File file) {
        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }


    @Override
    public void uploadFileURLtoDB(String fileURL, String fileName, String username, Long initiativeId) {
        try{
            User user=  userRepository.findByuserName(username);
            if(user == null) throw new UserException("Username is not found");
            Initiative initiative= initiativeRepository.findById(initiativeId).orElseThrow(()-> {return new InitiativeException("Initiative not found");});
            // Initiative initiative = new Initiative();
            //initiative.setId(initiativeId);
            com.revature.initiative.model.File file=new com.revature.initiative.model.File();
            com.revature.initiative.model.File fileTemp = fileRepository.findFileByFileURLAndInitiativeIdAndUploadedBy(fileURL, initiative, user);
            //com.revature.initiative.model.File fileTemp = file
            if(fileTemp ==null) {
                file.setFileURL(fileURL);
                file.setFileName(fileName);
                file.setInitiativeId(initiative);
                file.setUploadedBy(user);
                fileRepository.save(file);
            }else{
                throw new FileException("FileURL is already existed in the database");
            }
        }catch(NullPointerException e){
            e.printStackTrace();
        }catch (FileException f){
            System.out.println(f);
        }
    }
    }

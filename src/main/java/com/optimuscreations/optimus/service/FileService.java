package com.optimuscreations.optimus.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.optimuscreations.optimus.entity.File;
import com.optimuscreations.optimus.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    public List<File> getAllFiles() {
        return fileRepository.findAll();
    }

    public Optional<File> getFileById(Long id) {
        return fileRepository.findById(id);
    }

    public List<File> uploadFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<File> uploadedFiles = new ArrayList<>();
        
        System.out.println("Starting file upload process...");
        System.out.println("Bucket name: " + bucketName);

        for (MultipartFile multipartFile : multipartFiles) {
            System.out.println("Processing file: " + multipartFile.getOriginalFilename());
            String originalFilename = multipartFile.getOriginalFilename();
            String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFilename;

            // Set metadata
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(multipartFile.getContentType());
            metadata.setContentLength(multipartFile.getSize());

            try {
                // Upload to S3
                System.out.println("Attempting to upload to S3 with filename: " + uniqueFileName);
                amazonS3.putObject(new PutObjectRequest(
                    bucketName,
                    uniqueFileName,
                    multipartFile.getInputStream(),
                    metadata
                ));
                System.out.println("Successfully uploaded to S3");
            } catch (Exception e) {
                System.err.println("Error uploading to S3: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }

            // Get the S3 URL
            String s3Url = amazonS3.getUrl(bucketName, uniqueFileName).toString();

            // Create and save file entity
            File file = new File();
            file.setFilename(originalFilename);
            file.setS3Url(s3Url);
            
            uploadedFiles.add(fileRepository.save(file));
        }

        return uploadedFiles;
    }

    public void deleteFile(Long id) {
        Optional<File> fileOpt = fileRepository.findById(id);
        if (fileOpt.isPresent()) {
            File file = fileOpt.get();
            // Extract key from S3 URL
            String key = file.getS3Url().substring(file.getS3Url().lastIndexOf("/") + 1);
            // Delete from S3
            amazonS3.deleteObject(bucketName, key);
            // Delete from database
            fileRepository.deleteById(id);
        }
    }
}
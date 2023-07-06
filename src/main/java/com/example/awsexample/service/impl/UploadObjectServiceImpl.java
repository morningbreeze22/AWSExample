package com.example.awsexample.service.impl;

import com.example.awsexample.service.UploadObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.dynamodb.model.Put;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.io.InputStream;

@Service
public class UploadObjectServiceImpl implements UploadObjectService {

    private final String BASEPATH = "";
    private final String BUCKETNAME = "aws-s3-test-js";

    private final S3Client s3Client;

    @Autowired
    public UploadObjectServiceImpl(S3Client s3Client){
        this.s3Client = s3Client;
    }
    @Override
    public String uploadObject(MultipartFile file) {
        String path = BASEPATH + file.getOriginalFilename();
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                        .bucket(BUCKETNAME)
                        .key(path)
                        .build();
        try {
            InputStream inputStream = file.getInputStream();
            // if duplicated, will rewrite the file
            PutObjectResponse putObjectResponse = s3Client.putObject(putObjectRequest,
                    RequestBody.fromInputStream(inputStream, inputStream.available()));
            return putObjectResponse.eTag();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

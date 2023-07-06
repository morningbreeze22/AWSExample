package com.example.awsexample.service.impl;

import com.example.awsexample.service.DeleteObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;

@Service
public class DeleteObjectServiceImpl implements DeleteObjectService {

    private final String BASEPATH = "";
    private final String BUCKETNAME = "aws-s3-test-js";

    private final S3Client s3Client;

    @Autowired
    public DeleteObjectServiceImpl(S3Client s3Client){
        this.s3Client = s3Client;
    }

    @Override
    public String deleteObjectByKey(String key) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(BUCKETNAME)
                .key(key)
                .build();

        DeleteObjectResponse deleteObjectResponse = s3Client.deleteObject(deleteObjectRequest);

        if(deleteObjectResponse.sdkHttpResponse()!=null){
            return "deleted";
        }
        return "failed to delete";
    }
}

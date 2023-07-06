package com.example.awsexample.service.impl;

import com.example.awsexample.service.FindObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class FindObjectServiceImpl implements FindObjectService {
    private final String BASEPATH = "";
    private final String BUCKETNAME = "aws-s3-test-js";

    private final S3Client s3Client;

    @Autowired
    public FindObjectServiceImpl(S3Client s3Client){
        this.s3Client = s3Client;
    }


    // this method receives a file name and return list of paths for all files with that name
    @Override
    public List<String> findObjectPath(String name) {


        ListObjectsRequest listObjectsRequest = ListObjectsRequest.builder().bucket(BUCKETNAME).build();

        ListObjectsResponse listObjectsResponse = s3Client.listObjects(listObjectsRequest);
        List<String> paths = new ArrayList<>();
        for(S3Object s3Object: listObjectsResponse.contents()){

            if(s3Object.key().endsWith(name)){
                paths.add( "file path:" + s3Object.key()+ " etag:"+ s3Object.eTag());
            }

        }

        return paths;
    }

    // this method receives etag and will try finding the file path of that file
    @Override
    public String findObjectByETag(String eTag){
        ListObjectsRequest listObjectsRequest = ListObjectsRequest.builder().bucket(BUCKETNAME).build();

        ListObjectsResponse listObjectsResponse = s3Client.listObjects(listObjectsRequest);
        List<String> paths = new ArrayList<>();
        for(S3Object s3Object: listObjectsResponse.contents()){
            // here we use contains because s3Object.eTag() has double quote around it
            if(s3Object.eTag().contains(eTag)){
                return s3Object.key();
            }

        }

        return "failed to find one";
    }
}

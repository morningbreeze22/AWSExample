package com.example.awsexample.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadObjectService {
    String uploadObject(MultipartFile file);
}

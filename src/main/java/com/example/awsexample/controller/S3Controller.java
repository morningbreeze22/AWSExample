package com.example.awsexample.controller;

import com.example.awsexample.service.DeleteObjectService;
import com.example.awsexample.service.FindObjectService;
import com.example.awsexample.service.UploadObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class S3Controller {

    private final UploadObjectService uploadObjectService;
    private final FindObjectService findObjectService;

    private final DeleteObjectService deleteObjectService;

    @Autowired
    S3Controller(UploadObjectService uploadObjectService, FindObjectService findObjectService, DeleteObjectService deleteObjectService){
        this.uploadObjectService = uploadObjectService;
        this.findObjectService = findObjectService;
        this.deleteObjectService = deleteObjectService;
    }
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile multipartFile){
        String etag = uploadObjectService.uploadObject(multipartFile);
        return new ResponseEntity<>(etag, HttpStatus.OK);
    }

    @GetMapping("/path/{name}")
    public ResponseEntity<?> findFilePath(@PathVariable String name){
        List<String> path = findObjectService.findObjectPath(name);
        return new ResponseEntity<>(path, HttpStatus.OK);
    }

    @GetMapping("/etag/{etag}")
    public ResponseEntity<?> findFilePathByETag(@PathVariable String etag){
        String path = findObjectService.findObjectByETag(etag);
        return new ResponseEntity<>(path, HttpStatus.OK);
    }

    @DeleteMapping("etag/{etag}")
    public ResponseEntity<?> deleteFileByETag(@PathVariable String etag){
        String key = findObjectService.findObjectByETag(etag);
        String response = deleteObjectService.deleteObjectByKey(key);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

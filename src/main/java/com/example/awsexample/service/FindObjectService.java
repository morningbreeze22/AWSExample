package com.example.awsexample.service;

import java.util.List;

public interface FindObjectService {
    List<String> findObjectPath(String name);
    String findObjectByETag(String eTag);
}

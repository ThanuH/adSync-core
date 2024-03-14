package com.project.adsync.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {

    public String uploadFile(MultipartFile file) throws IOException;
}

package com.project.adsync.service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;

public interface CloudStorageService {
    ResponseEntity<ByteArrayResource> fetchAdvertisementFromCloud(String videoName);
}

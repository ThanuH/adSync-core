package com.project.adsync.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CloudStorageServiceImpl implements CloudStorageService {

    @Value("${gcp.storage.bucket.name}")
    private String bucketName;

    private final Storage storage;

    public CloudStorageServiceImpl() throws IOException {
        // Load credentials from the JSON file in resources folder
        GoogleCredentials credentials = GoogleCredentials.fromStream(
                new ClassPathResource("adsync-media-admin.json").getInputStream()
        );

        // Create a storage object using the loaded credentials
        this.storage = StorageOptions.newBuilder()
                .setCredentials(credentials)
                .build()
                .getService();
    }
    @Override
    public ResponseEntity<ByteArrayResource> fetchAdvertisementFromCloud(String videoName) {
        Blob blob = storage.get(bucketName, videoName);

        // Check if the blob exists
        if (blob != null && blob.exists()) {
            // Get the content of the blob as a byte array
            byte[] content = blob.getContent();

            // Create a ByteArrayResource with the content of the blob
            ByteArrayResource resource = new ByteArrayResource(content);

            // Return the video as ResponseEntity with appropriate headers
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("video/mp4")) // Adjust content type as per your video type
                    .body(resource);
        } else {
            // If the blob does not exist, return 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }
}

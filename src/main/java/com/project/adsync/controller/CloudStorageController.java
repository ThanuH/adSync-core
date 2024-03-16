package com.project.adsync.controller;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/adSync.api/cloudStorage")
@CrossOrigin(origins = "http://localhost:3000")
public class CloudStorageController {


    @Value("${gcp.storage.bucket.name}")
    private String bucketName;

    private final Storage storage;

    public CloudStorageController() throws IOException {
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

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        // Generate a unique filename for the uploaded file
        String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        // Create a blob in the storage bucket with the generated filename
        Blob blob = storage.create(
                Blob.newBuilder(bucketName, filename).build(),
                file.getBytes()
        );

        // Generate a reference link to the uploaded file
        String filNameReference =  filename;
        String fileUrl = "https://storage.googleapis.com/" + bucketName + "/" + filename;

        return filNameReference;
    }

    @GetMapping("/getvideo")
    public ResponseEntity<ByteArrayResource> getVideo(@RequestParam("videoName") String videoName){
        // Get the blob from Google Cloud Storage
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

    @DeleteMapping("deletefile")
    public ResponseEntity<String> deleteFile(@RequestParam("fileName") String fileName){
        try {
            // Get the blob from Google Cloud Storage
            Blob blob = storage.get(bucketName, fileName);

            // Check if the blob exists
            if (blob != null && blob.exists()) {
                // Delete the blob from Google Cloud Storage
                blob.delete();

                // Return success message
                return ResponseEntity.ok().body("File '" + fileName + "' deleted successfully.");
            } else {
                // If the blob does not exist, return 404 Not Found
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // If an error occurs, return 500 Internal Server Error
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting file '" + fileName + "'.");
        }
    }
}


package com.kirov.video_api.storage;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class StorageServiceImpl implements StorageService {
    private final String UPLOAD_LOCATION = "uploads";

    @Override
    public UUID upload(MultipartFile file) {
        if(file.isEmpty()) {
            throw new IllegalArgumentException("File is empty!");
        }

        String contentType = file.getContentType();
        if(contentType == null || !contentType.startsWith("video/")) {
            throw new IllegalArgumentException("Invalid file type. Only video files are supported!");
        }

        try {
            Path uploadsDirectory = Paths.get(UPLOAD_LOCATION);
            if(!Files.exists(uploadsDirectory)) {
                Files.createDirectory(uploadsDirectory);
            }

            UUID videoId = UUID.randomUUID();
            Path videoDirectory = uploadsDirectory.resolve(videoId.toString());
            Files.createDirectory(videoDirectory);
            Path videoPath = videoDirectory.resolve(file.getOriginalFilename());
            InputStream inputStream = file.getInputStream();
            Files.copy(inputStream, videoPath, StandardCopyOption.REPLACE_EXISTING);
            return videoId;
        } catch (IOException e) {
            throw new StorageException("Failed to save file: " + file.getOriginalFilename() + ", please try again later.");
        }
    }

    @Override
    public Path load(String id, String filename) {
        return Path.of(UPLOAD_LOCATION, id, filename);
    }
}

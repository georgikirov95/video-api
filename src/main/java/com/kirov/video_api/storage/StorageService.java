package com.kirov.video_api.storage;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.UUID;

public interface StorageService {
    public UUID upload(MultipartFile file);
    public Path load(String id, String filename);
}

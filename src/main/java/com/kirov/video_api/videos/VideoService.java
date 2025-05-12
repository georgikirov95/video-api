package com.kirov.video_api.videos;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface VideoService {
    public ApiResponse<Video> uploadVideo(MultipartFile videoFile);
    public ApiResponse<Video> extractAudio(UUID videoId);
    public Resource downloadAudio(UUID videoId);
}

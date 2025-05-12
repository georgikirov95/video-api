package com.kirov.video_api.videos;

import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/videos")
public class VideoController {

    private final VideoService videoService;

    @PostMapping
    public ResponseEntity<ApiResponse<Video>> uploadVideo(@RequestParam("video") MultipartFile video) {
        return ResponseEntity.ok(videoService.uploadVideo(video));
    }

    @PostMapping(path = "/{videoId}/extract-audio")
    public ResponseEntity<ApiResponse<Video>> extractAudio(@PathVariable UUID videoId) {
        return ResponseEntity.ok(videoService.extractAudio(videoId));
    }

    @GetMapping(path = "/{videoId}/download-audio")
    public ResponseEntity<Resource> downloadAudio(@PathVariable UUID videoId) {
        Resource audio = videoService.downloadAudio(videoId);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + audio.getFilename() + "\"")
                .body(audio);
    }
}

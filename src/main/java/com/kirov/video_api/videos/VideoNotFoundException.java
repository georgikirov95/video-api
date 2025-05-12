package com.kirov.video_api.videos;

public class VideoNotFoundException extends RuntimeException {
    public VideoNotFoundException(String message) {
        super(message);
    }
}

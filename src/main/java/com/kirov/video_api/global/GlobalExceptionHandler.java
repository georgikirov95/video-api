package com.kirov.video_api.global;

import com.kirov.video_api.storage.StorageException;
import com.kirov.video_api.videos.ApiResponse;
import com.kirov.video_api.videos.VideoNotFoundException;
import com.kirov.video_api.videos.ffmpeg.FfmpegException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StorageException.class)
    public ResponseEntity<ApiResponse<Void>> handleStorageException(StorageException ex) {
     return ResponseEntity.internalServerError().body(new ApiResponse<>(ex.getMessage(), "error",null));
    }

    @ExceptionHandler(FfmpegException.class)
    public ResponseEntity<ApiResponse<Void>> handleFfmpegException(FfmpegException ex) {
        return ResponseEntity.internalServerError().body(new ApiResponse<>(ex.getMessage(), "error",null));
    }

    @ExceptionHandler(VideoNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleVideoNotFoundException(VideoNotFoundException ex) {
        return new ResponseEntity<>(new ApiResponse<>(ex.getMessage(), "error",null), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handeIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(new ApiResponse<>(ex.getMessage(), "error", null));
    }
}

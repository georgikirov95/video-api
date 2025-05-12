package com.kirov.video_api.videos;

public record ApiResponse<T>(
    String message,
    String status,
    T data
) { }

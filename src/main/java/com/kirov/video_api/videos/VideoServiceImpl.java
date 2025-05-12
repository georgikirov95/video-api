package com.kirov.video_api.videos;

import com.kirov.video_api.storage.StorageService;
import com.kirov.video_api.videos.ffmpeg.Ffmpeg;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.UUID;

@AllArgsConstructor
@Service
public class VideoServiceImpl implements VideoService {
    private final VideoRepository videos;
    private final StorageService storage;
    private final Ffmpeg ffmpeg;
    private final String AUDIO = "audio.wav";

    @Override
    public ApiResponse<Video> uploadVideo(MultipartFile videoFile) {
        UUID videoId = storage.upload(videoFile);

        String[] fileParts = videoFile.getOriginalFilename().split("\\.");
        String name = fileParts[0];
        String extension = fileParts[1];

        return new ApiResponse<>(
                "Video uploaded successfully!",
                "success",
                videos.save(new Video(videoId, name, extension, false))
        );
    }

    @Override
    public ApiResponse<Video> extractAudio(UUID videoId) {
        Video video = getVideo(videoId);

        if(video.getAudioExtracted()) {
            throw new IllegalArgumentException("Audio already extracted!");
        }

        String videoFullName = video.getName() + "." + video.getExtension();

        Path videoPath = storage.load(videoId.toString(), videoFullName);
        Path audioOutputPath = storage.load(videoId.toString(), AUDIO);

        ffmpeg.extractAudio(videoPath, audioOutputPath);
        video.setAudioExtracted(true);

        return new ApiResponse<>(
                "Audio extracted successfully!",
                "success",
                videos.save(video)
        );
    }

    public Resource downloadAudio(UUID videoId) {
        Video video = getVideo(videoId);
        if(!video.getAudioExtracted()) {
            throw new IllegalArgumentException("Audio not extracted yet!");
        }
        Path audioPath = storage.load(videoId.toString(), AUDIO);
        return new FileSystemResource(audioPath.toFile());
    }

    private Video getVideo(UUID videoId) {
        return videos.findById(videoId)
                .orElseThrow(() -> new VideoNotFoundException("Video not found! ID: " + videoId));
    }
}

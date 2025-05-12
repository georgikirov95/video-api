package com.kirov.video_api.videos.ffmpeg;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;

@Service
public class FfmpegImpl implements Ffmpeg {
    @Override
    public void extractAudio(Path videoPath, Path outputAudioPath) {
        ProcessBuilder pb = new ProcessBuilder(
                "ffmpeg", "-i",
                videoPath.toString(), "-vn",
                "-acodec", "pcm_s16le",
                "-ar", "44100",
                "-ac", "2",
                outputAudioPath.toString()
        );
        pb.redirectErrorStream(true);

        try {
            Process ffmpegProcess = pb.start();
            int exitCode = ffmpegProcess.waitFor();
            if(exitCode != 0) {
                throw new FfmpegException("Failed extracting audio, please try again later.");
            }
        } catch (IOException | InterruptedException e) {
            throw new FfmpegException("Failed extracting audio, please try again later.");
        }
    }
}

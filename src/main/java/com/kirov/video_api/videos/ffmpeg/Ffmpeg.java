package com.kirov.video_api.videos.ffmpeg;

import java.nio.file.Path;

public interface Ffmpeg {
    public void extractAudio(Path videoPath, Path outputAudioPath);
}

package com.video.streamer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class VideoService {
    @Autowired
    private VideoStore videoStore;

    public Resource serveVideoResource(String show, int season, int episode) {
        String filePath = String.format("%s/S%d/S%02dE%02d.mp4", show, season, season, episode);
        return this.videoStore.getResource(filePath);
    }
}

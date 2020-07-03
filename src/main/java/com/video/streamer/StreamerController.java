package com.video.streamer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class StreamerController {
    @Autowired
    private VideoStore videoStore;

    @GetMapping("/shows")
    public List<String> getShows() {
        File[] dirs = new File("../../Desktop/Shows").listFiles(File::isDirectory);
        return Arrays.stream(dirs).map(dir -> dir.getName()).collect(Collectors.toList());
    }

    @GetMapping("/shows/{show}/{season}/{episode}")
    public ResponseEntity<?> getEpisode(@PathVariable String show,
                                        @PathVariable int season,
                                        @PathVariable int episode) throws IOException {
        String filePath = String.format("%s/S%d/S%02dE%02d.mp4", show, season, season, episode);
        Resource resource = this.videoStore.getResource(filePath);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(resource.contentLength());
        headers.set("Content-Type", "video/mp4");
        return new ResponseEntity<Object>(resource, headers, HttpStatus.OK);
    }
}

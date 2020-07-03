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
    private VideoService videoService;

    public static final String rootDir = "../../Desktop/Shows";

    @GetMapping("/shows")
    public List<String> getShows() {
        File[] dirs = new File(this.rootDir).listFiles(File::isDirectory);
        return Arrays.stream(dirs).map(dir -> dir.getName()).collect(Collectors.toList());
    }

    @GetMapping("/shows/{show}/seasons")
    public int getEpisodes(@PathVariable String show) {
        String showPath = String.format("%s/%s", this.rootDir, show);
        return new File(showPath).listFiles(File::isDirectory).length;
    }

    @GetMapping("/shows/{show}/{season_id}/episodes")
    public int getEpisodes(@PathVariable String show, @PathVariable int season_id) {
        String showPath = String.format("%s/%s/S%d", this.rootDir, show, season_id);
        return new File(showPath).list().length;
    }

    @GetMapping("/shows/{show}/{season}/{episode}")
    public ResponseEntity<?> getEpisode(@PathVariable String show,
                                        @PathVariable int season,
                                        @PathVariable int episode) throws IOException {
        Resource resource = this.videoService.serveVideoResource(show, season, episode);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(resource.contentLength());
        headers.set("Content-Type", "video/mp4");
        return new ResponseEntity<Object>(resource, headers, HttpStatus.OK);
    }
}

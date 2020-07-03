package com.video.streamer;

import org.springframework.content.fs.config.EnableFilesystemStores;
import org.springframework.content.fs.io.FileSystemResourceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
@EnableFilesystemStores
public class StoreConfig {
    File filesystemRoot() {
        return new File("../../Desktop/Shows");
    }

    @Bean
    public FileSystemResourceLoader fsResourceLoader() throws Exception {
        return new FileSystemResourceLoader(filesystemRoot().getAbsolutePath());
    }
}

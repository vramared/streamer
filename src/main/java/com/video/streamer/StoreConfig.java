package com.video.streamer;

import net.samuelcampos.usbdrivedetector.USBDeviceDetectorManager;
import org.springframework.content.fs.config.EnableFilesystemStores;
import org.springframework.content.fs.io.FileSystemResourceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

@Configuration
@EnableFilesystemStores
public class StoreConfig {
    public static File filesystemRoot() {
        return Arrays.stream(Objects.requireNonNull(new USBDeviceDetectorManager()
                .getRemovableDevices()
                .get(0)
                .getRootDirectory()
                .listFiles()))
                .filter(file -> file.getName().equals("Shows"))
                .findFirst()
                .get();
    }

    @Bean
    public FileSystemResourceLoader fsResourceLoader() throws Exception {
        return new FileSystemResourceLoader(filesystemRoot().getAbsolutePath());
    }
}

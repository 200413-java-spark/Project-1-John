package com.github.johnmedlockdev.project1john.utils;

import com.github.johnmedlockdev.project1john.business.SparkProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class LoadFile {

    @Autowired
    SparkProcess sparkProcess;

    public void process(MultipartFile multipartFile) {
        String name = multipartFile.getOriginalFilename();
        String fileName = "C:\\Storage\\" + name;

        try (BufferedWriter w = Files.newBufferedWriter(Paths.get(fileName))) {
            w.write(new String(multipartFile.getBytes()));
        } catch (IOException ioException) {
            ioException.printStackTrace();
            // TODO: 5/13/2020
        }
        sparkProcess.spark(fileName);
    }
}

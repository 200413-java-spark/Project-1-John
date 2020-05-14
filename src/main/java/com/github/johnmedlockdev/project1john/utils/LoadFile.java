package com.github.johnmedlockdev.project1john.utils;

import com.github.johnmedlockdev.project1john.business.SparkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class LoadFile {

    Logger logger = LoggerFactory.getLogger(LoadFile.class);

    @Autowired
    SparkService sparkService;

    public void process(MultipartFile multipartFile) {
        String name = multipartFile.getOriginalFilename();
        String fileName = "C:\\Storage\\" + name;

        try (BufferedWriter w = Files.newBufferedWriter(Paths.get(fileName))) {
            w.write(new String(multipartFile.getBytes()));
        } catch (IOException ioException) {
            logger.info("Something went wrong with io");
            ioException.printStackTrace();
        }
        sparkService.init(fileName);
    }
}

package com.github.johnmedlockdev.project1john.utils;

import com.github.johnmedlockdev.project1john.business.SparkProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

@Component
public class LoadFile {

    @Autowired
    SparkProcess sparkProcess;

    public void process(MultipartFile file) {
        File userFile = new File("C:\\Storage\\" + file.getOriginalFilename());
        String fileName = userFile.toString();

        try (FileOutputStream fOut = new FileOutputStream(userFile)) {
            fOut.write(file.getBytes());
            sparkProcess.spark(fileName);
        } catch (Exception exe) {
            exe.printStackTrace();
        }
    }
}

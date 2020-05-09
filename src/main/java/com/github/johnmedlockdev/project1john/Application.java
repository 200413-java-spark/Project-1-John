package com.github.johnmedlockdev.project1john;

import com.github.johnmedlockdev.project1john.models.SparkModel;
import com.github.johnmedlockdev.project1john.repositories.SparkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class Application implements CommandLineRunner {


    private static final Logger log = LoggerFactory.getLogger(Application.class);
    @Autowired
    private SparkRepository sparkRepository;

    public static void main(String[] args) throws IOException {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("StartApplication...");
        repo();
    }

    void repo() {
        log.info("inserting -> {}", sparkRepository.save(new SparkModel(45)));
        log.info("all {}",sparkRepository.findAll());
    }

}

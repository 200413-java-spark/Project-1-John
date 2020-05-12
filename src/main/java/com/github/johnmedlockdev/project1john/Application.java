package com.github.johnmedlockdev.project1john;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(Application.class, args);
    }
}


// logs inputs to jpa database
//    @Autowired
//    private SparkRepository sparkRepository;
//    void repo() {
//        log.info("inserting -> {}", sparkRepository.save(new SparkModel(45)));
//        log.info("all {}", sparkRepository.findAll());
//    }

//allow you to run like a main app
//public class Application implements CommandLineRunner {
//    @Override
//    public void run(String... args) {
//        log.info("StartApplication...");
//        log.info("inserting -> {}", sparkRepository.save(new SparkModel(45)));
//        log.info("all {}", sparkRepository.findAll());
//    }

//    how to instantiate a log


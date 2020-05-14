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

//// TODO: 5/9/2020 add proper logging

// TODO: 5/13/2020  maybe thing about refactoring the model so you don't have to drop the table everytime you hit the index
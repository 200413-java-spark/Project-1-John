package com.github.johnmedlockdev.project1john.controllers;

import com.github.johnmedlockdev.project1john.models.SparkModel;
import com.github.johnmedlockdev.project1john.repositories.SparkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class RestfulController {

    @Autowired
    SparkRepository sparkRepository;

    @GetMapping(path = "/graphApi")
    public List<SparkModel> graphApi() {
        return sparkRepository.findAll();
    }
}

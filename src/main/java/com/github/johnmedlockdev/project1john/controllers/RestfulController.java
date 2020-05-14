package com.github.johnmedlockdev.project1john.controllers;

import com.github.johnmedlockdev.project1john.models.SparkModel;
import com.github.johnmedlockdev.project1john.repositories.SparkRepository;
import com.github.johnmedlockdev.project1john.utils.LoadFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class RestfulController {

    Logger logger = LoggerFactory.getLogger(RestfulController.class);

    @Autowired
    SparkRepository sparkRepository;

    @GetMapping(path = "/graphApi")
    public List<SparkModel> graphApi() {
        logger.info("Request @ /graphApi");
        return sparkRepository.findAll(Sort.by(Sort.Direction.ASC, "price"));
    }
}

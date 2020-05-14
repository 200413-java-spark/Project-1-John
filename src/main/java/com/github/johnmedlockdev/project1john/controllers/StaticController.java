package com.github.johnmedlockdev.project1john.controllers;

import com.github.johnmedlockdev.project1john.repositories.SparkRepository;
import com.github.johnmedlockdev.project1john.utils.LoadFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class StaticController {

    Logger logger = LoggerFactory.getLogger(StaticController.class);

    @Autowired
    LoadFile loadFile;

    @Autowired
    SparkRepository sparkRepository;

    @RequestMapping(value = "/")
    public String index() {
        logger.info("Request @ /");
        sparkRepository.deleteAll();
        return "index";
    }

    @RequestMapping(value = "/graph")
    public String graph() {
        logger.info("Request @ /graph");
        return "graph";
    }

    @RequestMapping(value = "/graph", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String fileUpload(@RequestParam("file") MultipartFile file) {
        loadFile.process(file);
        return "graph";
    }
}



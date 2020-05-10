package com.github.johnmedlockdev.project1john.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StaticController {
    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/uploadForm")
    public String uploadForm() {
        return "uploadForm";
    }
}

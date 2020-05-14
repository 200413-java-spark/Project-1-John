package com.github.johnmedlockdev.project1john.controllers;

import com.github.johnmedlockdev.project1john.repositories.SparkRepository;
import com.github.johnmedlockdev.project1john.utils.LoadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class StaticController {

    @Autowired
    LoadFile loadFile;

    @Autowired
    SparkRepository sparkRepository;

    @RequestMapping(value = "/")
    public String index() {
        sparkRepository.deleteAll();
        return "index";
    }

    @RequestMapping(value = "/graph")
    public String graph() {
        return "graph";
    }

    @RequestMapping(value = "/graph", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String fileUpload(@RequestParam("file") MultipartFile file) {
        loadFile.process(file);
        return "graph";
    }
}


// // TODO: 5/9/2020 add a buffer to multipart
// // TODO: 5/9/2020 route user based off success or failure of upload.
//// TODO: 5/9/2020 add proper logging

//
////@Service
//public class WordCountService {
//
////    @Autowired
////    JavaSparkContext sc;
////
////    public Map<String, Long> getCount(List<String> wordList) {
////        JavaRDD<String> words = sc.parallelize(wordList);
////        Map<String, Long> wordCounts = words.countByValue();
////        return wordCounts;
////    }
//
//}


//@Configuration
//public class SparkConfig {
//
////    @Value("${spark.app.name}")
////    private String appName;
////    @Value("${spark.master}")
////    private String masterUri;
////
////    @Bean
////    public SparkConf conf() {
////        return new SparkConf().setAppName(appName).setMaster(masterUri);
////    }
////
////    @Bean
////    public JavaSparkContext sc() {
////        return new JavaSparkContext(conf());
////    }
//
//}

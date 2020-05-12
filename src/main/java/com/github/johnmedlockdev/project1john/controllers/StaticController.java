package com.github.johnmedlockdev.project1john.controllers;

import com.github.johnmedlockdev.project1john.models.SparkModel;
import com.github.johnmedlockdev.project1john.repositories.SparkRepository;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.storage.StorageLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;
import scala.Tuple2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
public class StaticController {

    @Autowired
    JavaSparkContext sc;

    @Autowired
    SparkRepository repository;

    private String fileName;

    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public RedirectView fileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        File convertFile = new File("C:\\Storage\\" + file.getOriginalFilename());

        fileName = convertFile.toString();

        try (FileOutputStream fout = new FileOutputStream(convertFile)) {
            fout.write(file.getBytes());
        } catch (Exception exe) {
            exe.printStackTrace();
        }
        return new RedirectView("spark");
    }

    @RequestMapping(value = "/spark")
    public RedirectView spark() {

        JavaRDD<String> allRows = sc.textFile(fileName).persist(StorageLevel.MEMORY_ONLY());

        List<String> headers = Arrays.asList(allRows.take(1).get(0).split(","));

        System.out.println(headers);
        String field = "Close";

//      all the data minus headers
        JavaRDD<String> data = allRows.filter(
                x -> !(x.split(",")[headers.indexOf(field)])
                        .equals(field));

//      selects the column
        JavaRDD<Double> column = data.map(
                x -> Double.valueOf(x.split(",")[headers.
                        indexOf(field)]));

        JavaRDD<Double> ceil = column.map(Math::ceil);

        JavaPairRDD<Double, Integer> priceMap = ceil.mapToPair((f) -> new Tuple2<>(f, 1));

        JavaPairRDD<Double, Integer> countValues = priceMap.reduceByKey((x, y) -> ((int) x + (int) y));

        List<Tuple2<Double, Integer>> list = countValues.collect();
        for (Tuple2<Double, Integer> x : list) {
            repository.save(new SparkModel(x._1, x._2));
        }


        return new RedirectView("/graph");


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

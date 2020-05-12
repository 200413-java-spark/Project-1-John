package com.github.johnmedlockdev.project1john.controllers;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
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
        return new RedirectView("spark"); // should go to d3 graph
    }

    @RequestMapping(value = "/spark")
    @ResponseBody
    public RedirectView spark() {

        JavaRDD<String> allRows = sc.textFile(fileName);
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
//        Persist session in memory
//        column.persist(StorageLevel.MEMORY_ONLY());

        JavaRDD<Double> ceil = column.map(Math::ceil);

        JavaPairRDD<Double, Integer> priceMap = ceil.mapToPair((f) -> new Tuple2<>(f, 1));

        JavaPairRDD<Double, Integer> countNames = priceMap.reduceByKey((x, y) -> ((int) x + (int) y));
        System.out.println(countNames.collect());


//
//        for(Double x : ceil.collect()){
//            System.out.println(x);
//        }


        return new RedirectView("/");


    }
}
//      ==   how you would use spark over array ==
//
//        SparkConf conf = new SparkConf().setAppName("appName").setMaster("local");
//        JavaSparkContext sc = new JavaSparkContext(conf);
//
//        List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);
//        JavaRDD<Integer> distData = sc.parallelize(data);

//       ===  how you would use spark over file  ==
//
//        JavaRDD<String> lines = sc.textFile("data.txt");
//        JavaRDD<Integer> lineLengths = lines.map(s -> s.length());
//        int totalLength = lineLengths.reduce((a, b) -> a + b);
//
//        == how you would save data ==
//
//        lineLengths.persist(StorageLevel.MEMORY_ONLY());
//
//        JavaRDD<String> lines = sc.textFile("data.txt");
//        JavaPairRDD<String, Integer> pairs = lines.mapToPair(s -> new Tuple2(s, 1));
//        JavaPairRDD<String, Integer> counts = pairs.reduceByKey((a, b) -> a + b);

//        Some notes on reading files with Spark:
//	*
//        If using a path on the local filesystem,
//        the file must also be accessible at the same path on worker nodes.
//        Either copy the file to all workers or use a network-mounted shared file system.
//                *
//                All of Spark’s file-based input methods,
//                including textFile, support running on directories, compressed files, and wildcards as well.
//                For example, you can use textFile("/my/directory"), textFile("/my/directory/*.txt"),
//                and textFile("/my/directory/*.gz").

//                Apart from text files, Spark’s Java API also supports several other data formats:
//	*
//        JavaSparkContext.wholeTextFiles lets you read a directory containing multiple small text files,
//        and returns each of them as (filename, content) pairs. This is in contrast with textFile,
//        which would return one record per line in each file.


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

package com.github.johnmedlockdev.project1john.business;

import com.github.johnmedlockdev.project1john.models.SparkModel;
import com.github.johnmedlockdev.project1john.repositories.SparkRepository;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.storage.StorageLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

@Component
public class SparkProcess {

    Logger logger = LoggerFactory.getLogger(SparkProcess.class);

    @Autowired
    JavaSparkContext sc;

    @Autowired
    SparkRepository repository;

    public void init(String filename) {
        JavaRDD<String> allRows = sc.textFile(filename).persist(StorageLevel.MEMORY_ONLY());
        logger.info("Cvs has been cached");

        List<Tuple2<Double, Integer>> list = transformFilter(allRows).collect();
        logger.info("Data has been transformed");

        persistToDb(list);
        logger.info("Persisted Data to Database");
    }

    private JavaPairRDD<Double, Integer> transformFilter(JavaRDD<String> allRows) {
        String field = "Close";

        List<String> headers = Arrays.asList(allRows.take(1).get(0).split(","));

        JavaRDD<String> data = allRows.filter(x -> !(x.split(",")[headers.indexOf(field)]).equals(field));
        logger.info("Removed headers");

        JavaRDD<Double> column = data.map(x -> Double.valueOf(x.split(",")[headers.indexOf(field)]));
        logger.info("Removed excess columns");

        JavaRDD<Double> ceil = column.map(Math::ceil);
        logger.info("normalized data");

        JavaPairRDD<Double, Integer> priceMap = ceil.mapToPair((f) -> new Tuple2<>(f, 1));
        logger.info("Mapped data to tuple");

        return priceMap.reduceByKey((x, y) -> ((int) x + (int) y));

    }

    private void persistToDb(List<Tuple2<Double, Integer>> list) {
        list.parallelStream().forEach(x -> repository.save(new SparkModel(x._1, x._2)));
    }

}

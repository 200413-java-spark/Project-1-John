package com.github.johnmedlockdev.project1john.business;

import com.github.johnmedlockdev.project1john.models.SparkModel;
import com.github.johnmedlockdev.project1john.repositories.SparkRepository;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.storage.StorageLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

@Component
public class SparkProcess {

    @Autowired
    JavaSparkContext sc;

    @Autowired
    SparkRepository repository;

    public void spark(String filename) {
        JavaRDD<String> allRows = sc.textFile(filename).persist(StorageLevel.MEMORY_ONLY());

        List<Tuple2<Double, Integer>> list = transformFilter(allRows).collect();

        persistToDb(list);
    }

    private JavaPairRDD<Double, Integer> transformFilter(JavaRDD<String> allRows) {
        List<String> headers = Arrays.asList(allRows.take(1).get(0).split(","));

        String field = "Close";

        JavaRDD<String> data = allRows.filter(
                x -> !(x.split(",")[headers.indexOf(field)])
                        .equals(field));

        JavaRDD<Double> column = data.map(
                x -> Double.valueOf(x.split(",")[headers.
                        indexOf(field)]));

        JavaRDD<Double> ceil = column.map(Math::ceil);

        JavaPairRDD<Double, Integer> priceMap = ceil.mapToPair((f) -> new Tuple2<>(f, 1));

        return priceMap.reduceByKey((x, y) -> ((int) x + (int) y));

    }

    private void persistToDb(List<Tuple2<Double, Integer>> list) {
        for (Tuple2<Double, Integer> x : list) {
            repository.save(new SparkModel(x._1, x._2));
        }
    }

}

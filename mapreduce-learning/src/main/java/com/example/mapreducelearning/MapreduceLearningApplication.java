package com.example.mapreducelearning;

import com.example.mapreducelearning.mapreduce.WordCount;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MapreduceLearningApplication {

    public static void main(String[] args) {
        SpringApplication.run(MapreduceLearningApplication.class, args);

        WordCount.countFileWord("hdfs:/input/test.txt", "hdfs:/output");
    }

}

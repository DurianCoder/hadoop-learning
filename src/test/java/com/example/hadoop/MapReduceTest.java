package com.example.hadoop;

import com.example.hadoop.mapreduce.WordCount;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author ying.jiang
 * @define MR
 * @date 2020-04-17-16:26:00
 */
@SpringBootTest
class MapReduceTest {

    @Test
    void testWordCount() {
        WordCount.countFileWord("hdfs:/input/test.txt", "hdfs:/output");
    }
}

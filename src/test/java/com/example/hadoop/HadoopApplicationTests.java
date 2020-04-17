package com.example.hadoop;

import com.example.hadoop.hdfs.HdfsFileSystemUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HadoopApplicationTests {

    @Test
    void contextLoads() {
        HdfsFileSystemUtils.catHdfsFile("hdfs:/input/test1.txt");
    }


}

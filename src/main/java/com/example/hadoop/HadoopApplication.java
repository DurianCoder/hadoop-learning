package com.example.hadoop;

import com.example.hadoop.hdfs.HdfsFileSystemUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class HadoopApplication {

    private static final Logger log = LoggerFactory.getLogger(HadoopApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(HadoopApplication.class, args);
        log.info("Hello world...");
        HdfsFileSystemUtils.createFile("hdfs:/input/test1.txt");
        HdfsFileSystemUtils.catHdfsFile("hdfs:/input/test1.txt");
    }

}

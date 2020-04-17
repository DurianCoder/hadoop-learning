package com.example.hadoop;

import com.example.hadoop.hdfs.HdfsFileSystemUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author ying.jiang
 * @define
 * @date 2020-04-17-13:59:00
 */

@SpringBootTest
class HdfsUtilTest {
    private static final Logger log = LoggerFactory.getLogger(HdfsUtilTest.class);

    @Test
    void catFile() {
        HdfsFileSystemUtils.catHdfsFile("hdfs:/input/test1.txt");
    }

    @Test
    void showFileMeta() {
        HdfsFileSystemUtils.showFileMeta("hdfs:/input/test1.txt");
    }

    @Test
    void createDir() {
        HdfsFileSystemUtils.createDir("hdfs:/input/tmp");
    }

    @Test
    void createFile() {
        HdfsFileSystemUtils.createDir("hdfs:/input/tmp/text.txt");
    }

    @Test
    void deleteFile() {
        HdfsFileSystemUtils.deleteFile("hdfs:/input/tmp/text.txt");
    }

    @Test
    void uploadFile() {
        HdfsFileSystemUtils.uploadFile("D:\\1537864674277.pdf", "hdfs:/input");
    }

    @Test
    void downloadFile() {
        HdfsFileSystemUtils.downloadFile("hdfs:/input/1537864674277.pdf", "F:\\");
    }

}

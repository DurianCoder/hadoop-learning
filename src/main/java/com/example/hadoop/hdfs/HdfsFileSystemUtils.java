package com.example.hadoop.hdfs;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

/**
 * @author ying.jiang
 * @define hdfs文件操作
 *
 * hdfs
 *  主要构成有：
 *      NameNode: 负责Client请求、心跳、负载均衡等
 *      DataNode: 负责数据存储
 *      SecondaryNameNode: 负责协助NameNode数据备份
 *  hdfs中的数据是已数据块来存储的，默认block大小为128M
 *
 * @date 2020-04-17-10:43:00
 */

@Slf4j
public class HdfsFileSystemUtils {
    private static final Logger log = LoggerFactory.getLogger(HdfsFileSystemUtils.class);

    private static Configuration conf;

    static {
        conf = new Configuration();
        // 设置HDFS访问地址
        conf.set("fs.default.name", "hdfs://centos01:9000");
    }

    /**
     * 打印文件内容
     *
     * @param filePath filePath
     */
    public static void catHdfsFile(String filePath) {
        log.info("hdfs file path:{}", filePath);
        try {
            FileSystem fs = FileSystem.get(conf);
            if (!fs.exists(new Path(filePath))) {
                log.warn("hdfs文件:{}不存在!", filePath);
                return;
            }
            InputStream in = fs.open(new Path(filePath));
            IOUtils.copyBytes(in, System.out, 4096, false);
            IOUtils.closeStream(in);
        } catch (Exception e) {
            log.error("[401:{}]", e.getMessage());
        }
    }


    /**
     * 创建文件夹
     *
     * @param filePath filePath
     */
    public static void createDir(String filePath) {
        log.info("create hdfs file path:{}", filePath);
        try {
            FileSystem fs = FileSystem.get(conf);
            boolean isOk = fs.mkdirs(new Path(filePath));
            if (isOk) {
                log.info("创建目录:{}成功", filePath);
            } else {
                log.info("创建目录:{}失败", filePath);
            }
        } catch (Exception e) {
            log.error("[401:{}]", e.getMessage());
        }
    }

    /**
     * 创建文件
     * @param filePath filePath
     */
    public static void createFile(String filePath) {
        log.info("create file file path:{}", filePath);
        try {
            FileSystem fs = FileSystem.get(conf);
            // FSDataOutputStream out = fs.create(new Path(filePath));
            // FSDataOutputStream outputStream = fs.append(new Path(filePath));  // 文件末尾追加
            FSDataOutputStream out = fs.create(new Path(filePath), () -> log.info("."));  //回调显示进度
            out.write("我是文件内容".getBytes());
            out.close();
            log.info("创建文件：{}成功", filePath);

        } catch (Exception e) {
            log.error("[401:{}]", e.getMessage());
        }
    }

    /**
     * 删除文件
     * @param filePath filePath
     * @return return
     */
    public static boolean deleteFile(String filePath) {
        log.info("delete file file path:{}", filePath);
        boolean isOk = false;
        try {
            FileSystem fs = FileSystem.get(conf);
            isOk = fs.deleteOnExit(new Path(filePath));
            
        } catch (Exception e) {
            log.error("[401:{}]", e.getMessage());
        }
        return isOk;
    }

    /**
     * 展示元数据
     * @param filePath filePath
     */
    public static void showFileMeta(String filePath) {
        log.info("file file path:{}", filePath);
        try {
            FileSystem fs = FileSystem.get(conf);
            FileStatus status = fs.getFileStatus(new Path(filePath));
            boolean directory = status.isDirectory();
            boolean file = status.isFile();
            String group = status.getGroup();
            long blockSize = status.getBlockSize();
            long accessTime = status.getAccessTime();

            log.info(String.valueOf(directory));
            log.info(String.valueOf(file));
            log.info(String.valueOf(group));
            log.info(String.valueOf(blockSize));
            log.info(String.valueOf(accessTime));

        } catch (Exception e) {
            log.error("[401:{}]", e.getMessage());
        }
    }

    /**
     * 上传本地文件到hdfs
     * @param filePath filePath
     * @param folderPath folderPath
     */
    public static void uploadFile(String filePath, String folderPath) {
        log.info("file file path:{}", filePath);
        try {
            FileSystem fs = FileSystem.get(conf);
            Path src = new Path(filePath);  // "D:/a.txt"
            Path dst = new Path(folderPath);  // "hdfs:/input"
            fs.copyFromLocalFile(src, dst);
        } catch (Exception e) {
            log.error("[401:{}]", e.getMessage());
        }
    }

    /**
     * 从hdfs下载文件到本地
     * @param filePath filePath
     * @param folderPath folderPath
     */
    public static void downloadFile(String filePath, String folderPath) {
        log.info("file file path:{}", filePath);
        try {
            FileSystem fs = FileSystem.get(conf);
            Path src = new Path(filePath);  // "hdfs:/input/a.txt"
            Path dst = new Path(folderPath);  // "D:/a.txt"
            fs.copyToLocalFile(false, src, dst, true);
        } catch (Exception e) {
            log.error("[401:{}]", e.getMessage());
        }
    }

}

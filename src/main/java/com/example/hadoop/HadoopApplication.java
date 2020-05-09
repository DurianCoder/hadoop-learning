package com.example.hadoop;

import com.example.hadoop.storm.wordcount.ReportBolt;
import com.example.hadoop.storm.wordcount.SentenceSpout;
import com.example.hadoop.storm.wordcount.SplitSentenceBolt;
import com.example.hadoop.storm.wordcount.WordCountBolt;
import lombok.extern.slf4j.Slf4j;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@SpringBootApplication
public class HadoopApplication {

    private static final Logger log = LoggerFactory.getLogger(HadoopApplication.class);
    public static void main(String[] args) throws Exception {
        SpringApplication.run(HadoopApplication.class, args);
        log.info("Hello world...");
//        HdfsFileSystemUtils.createFile("hdfs:/input/test1.txt");
//        HdfsFileSystemUtils.catHdfsFile("hdfs:/input/test1.txt");


        SentenceSpout sentenceSpout = new SentenceSpout();
        SplitSentenceBolt splitSentenceBolt = new SplitSentenceBolt();
        WordCountBolt wordCountBolt = new WordCountBolt();
        ReportBolt reportBolt = new ReportBolt();

        // 创建一个拓扑
        TopologyBuilder topologyBuilder = new TopologyBuilder();
        topologyBuilder.setSpout("sentenceSpout", sentenceSpout, 2).setNumTasks(4);
        topologyBuilder.setBolt("splitSentenceBolt", splitSentenceBolt, 2)
                .setNumTasks(4).shuffleGrouping("sentence-spout");
        topologyBuilder.setBolt("wordCountBolt", wordCountBolt, 2).setNumTasks(4)
                .fieldsGrouping("split-bolt", new Fields("word"));
        topologyBuilder.setBolt("reportBolt", reportBolt, 2).setNumTasks(4)
                .globalGrouping("count-bolt");

        Config config = new Config();
        LocalCluster localCluster = new LocalCluster();
        localCluster.submitTopology("word-count-topology", config, topologyBuilder.createTopology());
    }

}

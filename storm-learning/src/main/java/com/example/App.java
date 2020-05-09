package com.example;

import com.example.wordcount.ReportBolt;
import com.example.wordcount.SentenceSpout;
import com.example.wordcount.SplitSentenceBolt;
import com.example.wordcount.WordCountBolt;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        SentenceSpout sentenceSpout = new SentenceSpout();
        SplitSentenceBolt splitSentenceBolt = new SplitSentenceBolt();
        WordCountBolt wordCountBolt = new WordCountBolt();
        ReportBolt reportBolt = new ReportBolt();

        // 创建一个拓扑
        TopologyBuilder topologyBuilder = new TopologyBuilder();
        topologyBuilder.setSpout("sentence-spout", sentenceSpout, 2).setNumTasks(4);

        topologyBuilder.setBolt("split-bolt", splitSentenceBolt, 2).setNumTasks(4).shuffleGrouping("sentence-spout");

        topologyBuilder.setBolt("count-bolt", wordCountBolt, 2).setNumTasks(4).fieldsGrouping("split-bolt", new Fields("word"));

        topologyBuilder.setBolt("report-bolt", reportBolt, 2).setNumTasks(4).globalGrouping("count-bolt");

        Config config = new Config();
        LocalCluster localCluster = null;
        try {
            localCluster = new LocalCluster();
            localCluster.submitTopology("word-count-topology", config, topologyBuilder.createTopology());
        } catch (Exception e) {
            System.out.println("===============error===============");
            e.printStackTrace();
        }
    }
}

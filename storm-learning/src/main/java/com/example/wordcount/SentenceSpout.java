package com.example.wordcount;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import java.util.Map;
import java.util.Random;

/**
 * @author ying.jiang
 * @define SentenceSpout
 * @date 2020-05-09-17:14:00
 */
public class SentenceSpout extends BaseRichSpout {

    private SpoutOutputCollector spoutOutputCollector;
    private Random random;
    private String [] sentences = {
            "A topology runs forever, or until you kill it. Storm will automatically reassign any failed tasks. Additionally, Storm guarantees that there will be no data loss, even if machines go down and messages are dropped."
            , "Each node in a Storm topology executes in parallel. In your topology, you can specify how much parallelism you want for each node, and then Storm will spawn that number of threads across the cluster to do the execution."
            , "Links between nodes in your topology indicate how tuples should be passed around. For example, if there is a link between Spout A and Bolt B, a link from Spout A to Bolt C, and a link from Bolt B to Bolt C, then everytime Spout A emits a tuple, it will send the tuple to both Bolt B and Bolt C. All of Bolt B's output tuples will go to Bolt C as well."
    };

    /**
     * spout的初始化工作
     * @param map map
     * @param topologyContext topologyContext
     * @param spoutOutputCollector spoutOutputCollector
     */
    @Override
    public void open(Map<String, Object> map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.spoutOutputCollector = spoutOutputCollector;
        this.random = new Random();
    }

    /**
     * 每两秒随机发送一段sentence
     */
    @Override
    public void nextTuple() {
        Utils.sleep(2000);
        String sentence = sentences[random.nextInt(sentences.length)];
        this.spoutOutputCollector.emit(new Values(sentence));
    }


    /**
     * 定义字段名称
     * @param outputFieldsDeclarer outputFieldsDeclarer
     */
    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("sentence"));
    }
}

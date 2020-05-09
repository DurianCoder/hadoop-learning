package com.example.wordcount;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ying.jiang
 * @define ReportBolt
 * @date 2020-05-09-17:26:00
 */
public class ReportBolt extends BaseRichBolt {

    private HashMap<String, Integer> counts = null;

    @Override
    public void prepare(Map<String, Object> map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.counts = new HashMap<>();
    }

    @Override
    public void execute(Tuple tuple) {
        String word = tuple.getStringByField("word");
        int count = tuple.getIntegerByField("count");
        counts.put(word, count);
        ArrayList<Map.Entry<String, Integer>> list = new ArrayList<>(counts.entrySet());
        list.sort((o1, o2) -> (o2.getValue() - o1.getValue()));
        int n = Math.min(list.size(), 10);
        StringBuilder resultStr = new StringBuilder();
        for (int i = 0; i < n; i++) {
            Map.Entry<String, Integer> entry = list.get(i);
            String sortWord = entry.getKey();
            Integer sortCount = entry.getValue();
            resultStr.append(sortWord).append("------").append(sortCount).append("\n");
        }
        System.out.println("======================================计数结果======================================");
        System.out.println(this + "====" + word);
        System.out.println(resultStr.toString());

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("word", "count"));
    }

    @Override
    public void cleanup() {
        super.cleanup();
        System.out.println("---------------------final counts-----------------------------");
        for (String key : counts.keySet()) {
            System.out.println(key + " " + counts.get(key));
        }
        System.out.println("---------------------------------------------------------------");

    }
}

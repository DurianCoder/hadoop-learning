package com.example.hadoop.mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.StringTokenizer;

/**
 * @author ying.jiang
 * @define 单词统计
 * MapReduce是一个基于集群的计算平台，是一个简化分布式编程的计算框架，是一个将分布式计算抽象为Map和Reduce两个阶段的编程模型。
 * @date 2020-04-17-15:49:00
 */
public class WordCount {

    public static void countFileWord(String inputPath, String outPath) {
        try {
            // 实例化Config
            Configuration conf = new Configuration();
            // 设置HDFS访问地址
            conf.set("fs.default.name", "hdfs://centos01:9000");

            FileSystem fileSystem = FileSystem.get(conf);
            if (fileSystem.exists(new Path(outPath))) {
                fileSystem.delete(new Path(outPath), true);
            }

            // 构建任务对象
            Job job = Job.getInstance(conf, "word count");
            job.setJarByClass(WordCount.class);
            job.setMapperClass(TokenizerMapper.class);
            job.setCombinerClass(IntSumReducer.class);
            job.setReducerClass(IntSumReducer.class);

            // 设置输出结果类型
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(IntWritable.class);

            // 设置需要统计的文件的输入/输出路径
            FileInputFormat.addInputPath(job, new Path(inputPath));
            FileOutputFormat.setOutputPath(job, new Path(outPath));

            // 提交任务给hadoop集群
            System.exit(job.waitForCompletion(true) ? 0 : 1);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 自定义reduce内部类
     */
    private static class IntSumReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
        IntWritable result = new IntWritable();

        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
            // 统计单词总数
            int sum = 0;
            for (IntWritable value : values) {
                sum += value.get();
            }
            this.result.set(sum);

            // 输出统计结果
            context.write(key, this.result);
        }
    }

    /**
     * 自定义mapper
     */
    private static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {
        private static final IntWritable one = new IntWritable(1);
        private Text word = new Text();

        @Override
        protected void map(Object key, Text value, Mapper<Object, Text, Text, IntWritable>.Context context) throws IOException, InterruptedException {
            StringTokenizer tokenizer = new StringTokenizer(value.toString());
            while (tokenizer.hasMoreTokens()) {
                this.word.set(tokenizer.nextToken());
                // 输出单词与数量
                context.write(this.word, one);
            }
        }

    }


}

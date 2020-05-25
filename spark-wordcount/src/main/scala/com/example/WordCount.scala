package com.example

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * Hello world!
 *
 */
object WordCount {
  def main(args: Array[String]): Unit = {
    println( "Hello World!" )
    val conf = new SparkConf()
    conf.setAppName("WordCount")
    conf.setMaster("spark://centos01:7077")
    val sc = new SparkContext(conf)
    var linesRDD : RDD[String] = sc.textFile(args(0))
    var wordRDD: RDD[String] = linesRDD.flatMap(_.split(" "))
    var parseRDD: RDD[(String, Int)] = wordRDD.map((_, 1))

    var wordCountRDD: RDD[(String, Int)] = parseRDD.reduceByKey(_+_)
    var wordCountsSortRDD: RDD[(String, Int)] = wordCountRDD.sortBy(_._2, ascending = false)
    wordCountsSortRDD.saveAsTextFile(args(1))
    sc.stop()

//    var list = Array(1,2,3,4).map(_+1).filter(_>2)
//    for (elem <- list) {println(elem)}

  }
}

package com.durian.scala

import scala.collection.mutable.ListBuffer

/**
 * @define Todo
 * @author ying.jiang
 * @date 2020-05-19-09:51:00
 */
object CollectionDemo {

  def main(args: Array[String]): Unit = {
    val ints = new ListBuffer[Int]()
    ints += 1
    ints += 2
    ints += 3
    ints.foreach(println)
  }
}

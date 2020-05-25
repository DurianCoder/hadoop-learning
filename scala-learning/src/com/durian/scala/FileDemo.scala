package com.durian.scala

import scala.io.{BufferedSource, Source}

/**
 * @define Todo
 * @author ying.jiang
 * @date 2020-05-19-13:49:00
 */
object FileDemo {

  def main(args: Array[String]): Unit = {
    var source: BufferedSource = None : Option[BufferedSource]
    try{
      source = Source.fromFile("D:\\1.di1c")
      for (elem <- source.getLines()) {
        println(elem)
      }
    } catch {
      case e:Exception  => println("11Exception:", e.getMessage)
    } finally {
//      source.close()
    }



  }

}

package com.durian.scala

/**
 * @define Todo
 * @author ying.jiang
 * @date 2020-05-15-15:00:00
 */
object Main {
  def main(args: Array[String]): Unit = {
    val lucky = new Dog("lucky", 2)
    lucky.run()
    lucky.info
    lucky.eat
    lucky.sleep
  }
}

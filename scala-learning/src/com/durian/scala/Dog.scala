package com.durian.scala

/**
 * @define Todo
 * @author ying.jiang
 * @date 2020-05-15-14:48:00
 */
class Dog(var name: String, var age: Int = 10) extends Animal(name, age) {
  name = "dog"
  override def run(): Unit = println(s"$name running...")

  private[durian] def info = println(s"%age years old %name dog is running...")

  def eat: this.type = {
    println(s"$name eat...")
    this
  }

  def sleep  = {
    println(s"$name eat...")
    this
  }
}

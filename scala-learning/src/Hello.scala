import com.durian.scala.Dog

import scala.reflect.internal.util.StringOps

/**
 * @define Todo
 * @author ying.jiang
 * @date 2020-05-12-09:59:00
 */
object Hello extends App {

  override def main(args: Array[String]): Unit = {
    //    val greeter = new defaultGreeter()
    //    greeter.greet("ying.jiang")
    //
    //    val list: List[Int] = List(1, 2, 3).map(_ * 1)
    //    for (elem <- list) println(convertString(elem))
    //    "StringOps".capitalize

    //    var r = "[0-9]+".r
    //    var s = r.findAllIn("123 23 hello ")
    //    s.foreach(println)
    //
    //    for (elem <- 1 until 5) {
    //
    //    }

//    val durian = new Student("durian", 24)
//    println(durian._name)
    val lucky = new Dog("lucky", 2)
    lucky.run()

  }

  def convertString(x: Int): String = x match {
    case 0 => "zero"
    case 1 => "one"
    case _ => "other"
  }

}


class Point {
  private var _x = 0;
  private var _y = 0;
  private val bound = 100;


  def x = _x

  def x_=(newValue: Int): Unit = {
    if (newValue < bound) _x = newValue else printWarning
  }

  def y = _y

  def y_=(newValue: Int): Unit = {
    if (newValue < bound) _y = newValue else printWarning
  }

  private def printWarning = println("WARNING: Out of bounds")

}


trait Greeter {
  def greet(name: String): Unit
}

class defaultGreeter extends Greeter {
  override def greet(name: String): Unit = {
    println("Hello, ", name, "What are you doing!")
  }
}


class Student( name: String,  age: Int) {

  var sex: String = "man"
  var _name = name

  def info: Unit = {
    println(s"name:$name, age:$age")
  }
}
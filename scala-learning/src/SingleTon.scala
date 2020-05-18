/**
 * @define Todo
 * @author ying.jiang
 * @date 2020-05-15-09:54:00
 */
object SingleTon {
  def main(args: Array[String]): Unit = {
    val instance = Book.getInstance
    println(instance.getClass.getName)
  }
}



class Book private {

}


object Book {
  var book = new Book
  def getInstance = book
}
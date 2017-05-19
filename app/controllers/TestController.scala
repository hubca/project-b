package controllers

/**
  * Created by sambo on 19/05/2017.
  */
object TestController {

  def main(args: Array[String]): Unit = {

    val list1 = List(1, 1, 2, 3, 5, 8)
    println(executeTimePrint(reverse1(list1)))
    println(executeTimePrint(reverse2(list1)))
    println(executeTimePrint(reverse3(list1)))
    println(executeTimePrint(reverse4(list1)))
    println(executeTimePrint(reverse5(list1)))

    val list2 = List(1, 2, 3, 2, 1)
    list2.map(_.toString)
    //println(executeTimePrint(isPalindrome1(list2)))
    //println(executeTimePrint(isPalindrome2(list2)))
  }

  def reverse1(ls: List[Int]): List[Int] = ls.reverse // procedural

  def reverse2(ls: List[Int]): List[Int] = ls match { // recursive
    case Nil => ls
    case x :: xs => reverse2(xs) ::: List(x)
  }

  // val list1 = List(1, 1, 2, 3, 5, 8)
  // 1) ls = List(1, 1, 2, 3, 5, 8)
  // 2) ls = List()


  def reverse3(ls1: List[Int]): List[Int] = { // recursive
    def _rm3(ls2: List[Int]): List[Int] = ls2 match {
      case Nil => ls2
      case x :: xs => _rm3(xs) ::: List(x)
    }
    _rm3(ls1)
  }

  def reverse4(ls1: List[Int]): List[Int] = { // tail recursive
    def _rm4(lsResult: List[Int], lsRemain: List[Int]): List[Int] = lsRemain match {
      case Nil => lsResult
      case x :: xs => _rm4(x :: lsResult, xs)
    }
    _rm4(Nil, ls1)
  }
  // 1) _l = Nil, _r = List(1, 1, 2, 3, 5, 8)
  // 2) _l = List(1, Nil), _r = List(1, 2, 3, 5, 8)
  // 3) _l = List(1, 1, Nil), _r = List(2, 3, 5, 8)
  // 4) _l = List(2, 1, 1, Nil), _r = List(3, 5, 8)
  // 5) _l = List(3, 2, 1, 1, Nil), _r = List(5, 8)
  // 6) _l = List(5, 3, 2, 1, 1, Nil), _r = List(8)
  // 7) _l = List(8, 5, 3, 2, 1, 1, Nil), _r = List()

  def reverse5(ls: List[Int]): List[Int] = ls.foldLeft(List[Int]()) { (r, h) => h :: r } // fold left

  // used to execute function, measure execution time & print results
  def executeTimePrint(codeBlock: => List[Int]): Unit = {
    val t0 = System.nanoTime()
    val result = codeBlock    // call-by-name
    val t1 = System.nanoTime()

    println(s"Elapsed time: ${t1 - t0}ns\n$result")
  }

}

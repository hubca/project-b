package controllers

/**
  * Created by boseph on 23/04/2017.
  */
object KnapsackProblem {

  def main(args: Array[String]): Unit = {

    val smallArr = Array(159, 162, 224, 128, 162, 3, 217, 208)
    val mediumArr = Array(156, 108, 125, 58, 211, 162, 163, 52, 208, 181, 205, 68, 297, 131, 162, 197, 111, 166, 55, 131, 440, 138, 349, 165, 205)
    val bigArr = Array(156, 108, 125, 58, 211, 162, 163, 52, 208, 181, 205, 68, 297, 131, 162, 197, 111, 166, 55, 131, 440, 138, 349, 165, 205, 156, 157, 162, 159, 342, 58, 414, 370, 178, 505, 111, 327, 330, 163, 156, 168, 168, 194, 153, 54, 238, 110, 127, 254, 270, 293, 165, 58, 107, 222, 239, 54, 208, 162, 199, 159, 158, 165, 156, 168, 217)
    val limit = 4500

    try {
      val arr = mediumArr
      //executeTimePrint(method1(arr, limit).toList)
      //executeTimePrint(method2(arr, limit).toList)
      //executeTimePrint(method3(arr, limit).toList)
      executeTimePrint(method4(arr, limit).toList)
    } catch {
      case ex: UnsupportedOperationException => println("Limit cannot be matched exactly so doesn't work with method 1")
      case _: Throwable => println("Got some other kind of exception")
    }

  }

  // creates all subset combinations - memory heap error for bigger arrays
  def method1(arr: Array[Int], limit: Int): Array[Int] = {
    val list1 = arr.toSet.subsets.map(_.toList).toList
    val x = for {el <- list1 if el.sum == limit} yield el
    val bestList = x.maxBy(_.sum)
    bestList.toArray
  }

  // recursive solution - obtained from http://stackoverflow.com/questions/43429023/get-sum-of-combinations-of-array-values-in-scala
  def method2(arr: Array[Int], limit: Int): Array[Int] = {
    scala.util.Sorting.quickSort(arr)
    val subset = arr.filter(limit.>=)
    if (subset.isEmpty) Array()
    else (1 to subset.length).flatMap(subset.combinations)
      .find(_.sum == limit)
      .fold(method2(subset, limit-1))(identity)
  }

  // tail recursive solution - obtained from http://stackoverflow.com/questions/43429023/get-sum-of-combinations-of-array-values-in-scala
  def method3(arr: Array[Int], limit: Int): Array[Int] = {
    val subset = arr.filter(limit.>=)
    if (subset.sum <= limit) subset
    else {
      val res = (1 to subset.length).view
        .flatMap(subset.combinations)
        .find(_.sum == limit)
      if (res.isEmpty) method3(subset, limit-1)
      else res.get
    }
  }

  // tail recursive solution with pattern matching (update to method3)
  def method4(arr: Array[Int], limit: Int): Array[Int] = {
    val subset = arr.filter(limit.>=)

    subset match {
      case subset if subset.sum <= limit => subset
      case _ => val res = (1 to subset.length).view
        .flatMap(subset.combinations)
        .find(_.sum == limit)
        if (res.isEmpty) method3(subset, limit-1)
        else res.get
    }
  }

  // used to execute function, measure execution time & print results
  def executeTimePrint(codeBlock: => List[Int]): Unit = {
    val t0 = System.nanoTime()
    val result = codeBlock    // call-by-name
    val t1 = System.nanoTime()

    println(s"Elapsed time: ${t1 - t0}ns\n$result = ${result.sum}")
  }
}
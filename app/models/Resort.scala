package models

/**
  * Created by sambo on 22/05/2017.
  */

// TODO - determine database
case class ResortStatic(id: Int, name: String, countryCode: String, country: String, latLongLst: List[Double], areaSize_km2: Double, nearestIata: String)
case class Resort(static: ResortStatic)

//case class Resort[A](sMap: Map[String, ResortStatic])
//case class Person(name: String, age: Int)

object Resort {

  def main(args: Array[String]): Unit = {

    //val person1 = Person("Samuel", 36)
    val resortStatic1 = ResortStatic(1, "Chamonix", "FRA", "France", List(50.114929, -122.948626), 18.2, "GVA")

    val resort1 = Resort(resortStatic1)
    println(resort1.static.latLongLst(1))
    //val resort1 = Resort(sMap)

    /*

    //val resort2 = Resort(map1("id" => "2", "name" => "Chamonix"))

    val list1 = List(50.114929, -122.948626)
    //val map1 = list1.map(c => c * 2)
    val r1 = resort1.sMap("rLatLongLst")

    val list2 = List(1, 2, List("a", "b", "c"))
    //val list3 = List("a", "b", "c")
    println(list2(2).get(0))
    */
    //printDistance
    //println(roundSmart(7.9121))
  }
}
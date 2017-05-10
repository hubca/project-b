package controllers

import scala.collection.immutable.HashMap
import scala.collection.mutable

/**
  * Created by sambo on 10/05/2017.
  */
object testBoardrs {

  def main(args: Array[String]): Unit = {

    val uData = Array(0.5223, 1.72314)
    val rData = Array(1.268921, -1.95231)

    println(testCollections1)
  }

  def testCollections1: Boolean = Vector(1, 2, 3) == Range(1, 2, 3)
  //def mapToList[a, b](m: Map[a, b]): List[(a, b)] = m.toList

  def getDistance(uLoc: Array[Double], rLoc: Array[Double]): Double = {
    // rLat, rLong
    uLoc(0)
  }

  /*
  getDistance : function(resortLat, resortLon) {

    var p1 = this.getUserLocation();
    var p2 = this.getResortLocation(resortLat, resortLon);

    var R = 6378137; // Earth’s mean radius in meter
    var dLat = rad(p2.lat - p1.lat);
    var dLong = rad(p2.lng - p1.lng);
    var a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
      Math.cos(rad(p1.lat)) * Math.cos(rad(p2.lat)) *
        Math.sin(dLong / 2) * Math.sin(dLong / 2);
    var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    var d = R * c;
    var km = Math.round(d/1000);
    var miles = Math.round(d/1600);

    return miles; // returns the distance in miles
  },
  */
}

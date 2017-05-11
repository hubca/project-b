package controllers

import scala.collection.immutable.HashMap
import scala.collection.mutable

/**
  * Created by sambo on 10/05/2017.
  */
object testBoardrs {

  def main(args: Array[String]): Unit = {

    val uLoc = Array(51.468125, -0.127886)
    val rLoc = Array(45.955767, 6.885117)

    println(getDistance(uLoc, rLoc))
  }

//  def testCollections1: Boolean = Vector(1, 2, 3) == Range(1, 2, 3)
  //def mapToList[a, b](m: Map[a, b]): List[(a, b)] = m.toList

  def rad(x: Double):Double = x * (scala.math.Pi / 180)

  def getDistance(uLoc: Array[Double], rLoc: Array[Double]): Double = {
    val r = 6378137// Earth’s mean radius in metres
    val dLat = rad(uLoc(0) - rLoc(0))
    val dLong = rad(uLoc(1) - rLoc(1))
    val a = scala.math.sin(dLat / 2) * scala.math.sin(dLat / 2) +
            scala.math.cos(rad(uLoc(0))) * scala.math.cos(rad(rLoc(0))) *
            scala.math.sin(dLong / 2) * scala.math.sin(dLong / 2)
    val c = 2 * scala.math.atan2(scala.math.sqrt(a), scala.math.sqrt(1 - a))
    val d = r * c
    d/1000// in km
    d/1600// in miles // scala.math.round(d/1600);
  }

  /*
  RESORT DATA
    STATIC
    + rId : Int // unique ID of the resort
    + rName : String // name of the resort
  	+ rCountryCode : String (3 char) // country code of the resort
  	+ rCountry : String // country of the resort
	  + rLatLongLst : Collection (List?) // latitude, longitude of the resort
  	+ rAreaSize_km2 : Double // boardable size of the resort in km2
  	? rNearestIata : String (3 char) // nearest International Airport (code) to the resort
  	? rNearestAirport : String (3 char) // nearest Local Airport (code) to the resort

    DYNAMIC
    + rAvgAnnualVisitors : Double : db updated annually  // average annual visitors to the resort

      DATE DEPENDANT
      + lastDateOpenClosedLst : Collection (List?) : db updated daily // last open, closed date of resort
      + nextDateOpenClosedLst : Collection (List?) : db updated daily // next open, closed date of resort
      + rWeatherMap : Map : db added daily // collection of weather attributes of resort
            + rWmIcon : String // icon (html/url?) representing expected weather of resort
            + rWmSummary : String // short summary of expected weather of resort
            + rWmSnowfall_cm : Double // expected snowfall of resort in cm
            ? rWmRainfall_cm : Double // expected rainfall of resort in cm
            + rWmTempMin_c : Double // expected minimum temperature of resort in degrees celsius
            + rWmTempMax_c : Double // expected maximum temperature of resort in degrees celsius
            + rWmWindSpeed_mph : Double // expected wind speed of resort in miles per hour
            + rWmVisibility_mi : Double // expected visibility of resort in miles

      USER DEPENDANT
      + rDistance_km: Double // distance from user to resort in km
      + rCostMap : Map : API/cache? // collection of weather attributes of resort
            + rCstTravelMap : Map : API/cache? // collection of travelling costs
              + rCstTmOutLst : List[Double] : API/Cache // itemised cost of each leg of the outbound journey to resort (p2p)
              + rCstTmInLst : List[Double] : API/Cache // itemised cost of each leg of the inbound journey from resort (p2p)
            + rCstAccommodation_usd : Double // best price of accommodation at resort for designated times
            + rCstLiftPass_usd : Double // best price of lift pass at resort for designated times


	USER DATA
	  GEOGRAPHIC DEPENDANT
	  + uLatLongLst: static : Collection (List?)
	  + uDistanceMetric : static : String
	  + uCurrencyHtml : static : String

	  DEMOGRAPHIC DEPENDANT
	  +

	  OCCASION DEPENDANT
	  +
  },
  */
}

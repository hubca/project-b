package models

/**
  * Created by sambo on 17/06/2017.
  */

case class SunInfoResults(sunrise: String, sunset: String, solar_noon: String, day_length: Long, civil_twilight_begin: String, civil_twilight_end: String, nautical_twilight_begin: String, nautical_twilight_end: String, astronomical_twilight_begin: String, astronomical_twilight_end: String)
case class SunInfo(results: SunInfoResults, status: String)
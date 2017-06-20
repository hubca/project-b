package services

import javax.inject.Inject
import play.api.libs.json._
import scala.concurrent.{ExecutionContext, Future}
import models.{SunInfo, SunInfoResults}

class ServiceWeather @Inject() (sc: ServiceClient)(implicit exec: ExecutionContext) {

  def makeServiceCall(lat: Double, lng: Double): Future[JsResult[SunInfo]] = {

    implicit val sunInfoResultsReads = Json.reads[SunInfoResults]
    implicit val sunInfoReads = Json.reads[SunInfo]

    sc.makeCall(s"http://api.sunrise-sunset.org/json?lat=$lat&lng=$lng&formatted=0").map { response =>
      val jsonString = response.json
      Json.fromJson[SunInfo](jsonString)
    }
  }

}

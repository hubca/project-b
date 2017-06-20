package controllers

import javax.inject.Inject
import play.api.mvc.{Action, Controller}
import play.api.libs.json._
import play.api.libs.ws.{WSClient, WSResponse}
import scala.concurrent.{ExecutionContext, Future}
import models.{SunInfo, SunInfoResults}
import services.{ServiceClient, ServiceWeather}

/**
  * Created by bosis on 14/06/2017.
  */

class JsonController @Inject() (ws: WSClient)(sw: ServiceWeather)(implicit exec: ExecutionContext) extends Controller {

  // call weather API returning html or error string (logic in services/ServiceWeather)
  def readJson = getWeatherResponse(-33.8830, 151.2167)

  def getWeatherResponse(lat: Double, lng: Double) = Action.async { request =>

    sw.makeServiceCall(lat,lng).map { response =>

      response match {
        case JsSuccess(r: SunInfo, path: JsPath) => Ok(views.html.sun(r))
        case e: JsError => Ok(s"Errors: ${JsError.toJson(e).toString()}")
      }

    }

  }


  // passing a JSON value returning a string or static error message from the following (mock) JSON post:
  // curl -i -H "Content-type: application/json" -X POST -d "{"""name""":"""Burt Bacarak Jr."""}" http://localhost:9000/postJson
  def postJson = Action(parse.json) { request =>
    (request.body \ "name").asOpt[String].map {name =>
      Ok("Hello " + name)
    }.getOrElse {
      BadRequest("Missing parameter [name]")
    }
  }

  /*
    // call weather API returning html or error string (all logic here)
    def readJson1 = Action.async { request =>

    implicit val sunInfoResultsReads = Json.reads[SunInfoResults]
    implicit val sunInfoReads = Json.reads[SunInfo]

    ws.url("http://api.sunrise-sunset.org/json?lat=-33.8830&lng=151.2167&formatted=0").get().map { response =>

      //val json = request.body
      val jsonString = response.json
      val sunInfoFromJson: JsResult[SunInfo] = Json.fromJson[SunInfo](jsonString)

      sunInfoFromJson match {
        case JsSuccess(r: SunInfo, path: JsPath) => Ok(views.html.sun(r))
        case e: JsError => Ok(s"Errors: ${JsError.toJson(e).toString()}")
      }
    }

  }

  // passing two JSON values returning a string or controlled error message from the following (mock) JSON post:
  // curl -i -H "Content-type: application/json" -X POST -d "{"""name""":"""Burt Bacarak Jr.""", """age""": 22}" http://localhost:9000/postJson
  def postJson2 = Action(parse.json) { request =>
    var jsVal = request.body
    val name = (jsVal \ "name").validate[String]
    val age = (jsVal \ "age").validate[Int]

    val list1 = List(name, age).map( x => x match {
      case s: JsSuccess[String] => x.get
      case e: JsError => "Errors: " + JsError.toJson(e).toString()
    })

    Ok(list1.mkString(" is "))
  }

  // validating a JSON value (with validation) returning a string or controlled error message from the following (mock) JSON post:
  // curl -i -H "Content-type: application/json" -X POST -d "{"""name""":"""Burt Bacarak Jr."""}" http://localhost:9000/postJson
  def postJson3 = Action(parse.json) { request =>

  implicit val jsonReads = Json.reads[String]

  request.body.validate[String].map {
    case name => Ok("Hello " + name)
  }.recoverTotal{
    e => BadRequest("Detected error:"+ JsError.toJson(e))
  }

  }

  // mapping a JSON object to a (case) class returning a two valued string or controlled error message from the following (mock) JSON post:
  // curl -i -H "Content-type: application/json" -X POST -d "{"""name""":"""Burt Bacarak Jr.""", """age""": 22}" http://localhost:9000/postJson
  // using: case class Person(name: String, age: Int)
  def postJson4 = Action(parse.json) { request =>

  implicit val personReads = Json.reads[Person]

  var jsonString = request.body
  val personFromJson: JsResult[Person] = Json.fromJson[Person](jsonString)

  val result = personFromJson match {
    case JsSuccess(r: Person, path: JsPath) => s"Name: ${r.name}, Age: ${r.age}"
    case e: JsError => s"Errors: ${JsError.toJson(e).toString()}"
  }

  Ok(result)
  }

  // mapping a nested JSON object to a (case) class returning a string or controlled error message from the following (mock) JSON post:
  // curl -i -H "Content-type: application/json" -X POST -d "{"""name""":{"""firstName""":"""Burt""", """lastName""":"""Bacarak Jr."""}, """age""": 22}" http://localhost:9000/postJson
  // using: case class PersonName(firstName: String, lastName: String); case class Person(name: PersonName, age: Int)
  def postJson5 = Action(parse.json) { request =>
    implicit val personNameReads = Json.reads[PersonName]
    implicit val personReads = Json.reads[Person]

    var jsonString = request.body
    val personFromJson: JsResult[Person] = Json.fromJson[Person](jsonString)

    val result = personFromJson match {
      case JsSuccess(r: Person, path: JsPath) => s"Name: ${r.name.firstName} ${r.name.lastName}, Age: ${r.age}"
      case e: JsError => s"Errors: ${JsError.toJson(e).toString()}"
    }

    Ok(result)
  }

  // TODO - implement IP address to use reoccurringly in order to generate user data and user dependant data (as done with php version)
  def getUserIpAddress = Action { request =>
    Ok(request.remoteAddress)
  }

*/
}

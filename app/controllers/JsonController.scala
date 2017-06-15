package controllers

import javax.inject.Inject

import play.api.mvc.{Action, Controller}

/**
  * Created by sambo on 14/06/2017.
  */
class JsonController @Inject() extends Controller {

  def sayHello = Action(parse.json) { request =>
    var rolejson = request.body
    val name = (rolejson \ "name").as[String]
    val age = (rolejson \ "age").as[Int]

    Ok(s"name is $name and age is $age")
  }

  /*
  def sayHello = {
    implicit val personReads = (
      (__ \ 'name).read[String] and
        (__ \ 'age).read[Int]
      )(Person)
    Ok(personReads)
  }

  def sayHello(implicit json: JsValue) = Action(parse.json) { request =>
    for {
      name <- (json \ "name").validate[String]
      age <- (json \ "age").validate[String]
    } yield Ok("Hello " + name + ", age " + age)
  }

  def sayHello = Action(parse.json) { request =>
    request.body.validate[(String, String)].map {
      case (name, age) => Ok("Hello " + name + ", age " + age)
    }.getOrElse {
      BadRequest("Missing parameter [name]")
    }
  }

  Action(parse.json) { request =>
    request.body.validate[(String, Long)].map {
      case (name, age) => Ok("Hello " + name + ", you're "+age)
    }.recoverTotal{
      e => BadRequest("Detected error:"+ JsError.toJson(e))
    }
  }

  def sayHello = Action(parse.json) { request =>
    (request.body \ "name").asOpt[String].map {name =>
      Ok("Hello " + name)
    }.getOrElse {
      BadRequest("Missing parameter [name]")
    }
  }
  def sayHello = Action(parse.json) { request =>
    request.body.validate[String].map {
      case name => Ok("Hello " + name)
    }.recoverTotal{
      e => BadRequest("Detected error:"+ JsError.toJson(e))
    }
  }

  */

}

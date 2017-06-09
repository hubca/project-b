package controllers

import akka.actor.ActorSystem
import javax.inject._

import play.api.mvc._
import services.ServiceClient

import scala.concurrent.ExecutionContext

@Singleton
class Async2Controller @Inject() (sc: ServiceClient)(actorSystem: ActorSystem)(implicit exec: ExecutionContext) extends Controller {

  def index(embed: Boolean = false) = Action.async { request =>

    val asy2 = sc.makeServiceCall("async2")

    for {
      async2Message <- asy2
    } yield {
      if(embed) Ok(views.html.async2.async2Body(async2Message))
      else Ok(views.html.async2.async2(async2Message))
    }
  }
}
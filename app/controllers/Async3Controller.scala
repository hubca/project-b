package controllers

import akka.actor.ActorSystem
import javax.inject._

import play.api.mvc._
import services.ServiceClient

import scala.concurrent.ExecutionContext

@Singleton
class Async3Controller @Inject() (sc: ServiceClient)(actorSystem: ActorSystem)(implicit exec: ExecutionContext) extends Controller {

  def index(embed: Boolean = false) = Action.async { request =>

    val asy3 = sc.makeServiceCall("async3")

    for {
      async3Message <- asy3
    } yield {
      if(embed) Ok(views.html.async2.async2Body(async3Message))
      else Ok(views.html.async2.async2(async3Message))
    }
  }
}
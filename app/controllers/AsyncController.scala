package controllers

import akka.actor.ActorSystem
import javax.inject._
import play.api.mvc._
import services.ServiceClient
import scala.concurrent.ExecutionContext

@Singleton
class AsyncController @Inject() (sc: ServiceClient)(actorSystem: ActorSystem)(implicit exec: ExecutionContext) extends Controller {

  def index = Action.async { request =>

    val asy1 = sc.makeServiceCall("async1")

    for {
      async1Message <- asy1
    } yield {
      Ok(views.html.async1.async1(async1Message))
    }
  }

}
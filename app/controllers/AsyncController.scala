package controllers

import javax.inject._
import play.api.mvc._
import services.ServiceClient
import scala.concurrent.ExecutionContext

@Singleton
class AsyncController @Inject() (sc: ServiceClient)(implicit exec: ExecutionContext) extends Controller {

  def index(embed: Boolean = false) = Action.async { request =>

    val asy1 = sc.makeServiceCall("async1")

    for {
      async1Message <- asy1
    } yield {
      if(embed) Ok(views.html.async1.async1Body(async1Message))
      else Ok(views.html.async1.async1(async1Message))
    }
  }
}
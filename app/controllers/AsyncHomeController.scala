package controllers

import javax.inject.Inject

import play.api.mvc._
import scala.concurrent.{ExecutionContext, Future}
import data.Pagelet
import akka.stream.Materializer

class AsyncHomeController @Inject() (as1: AsyncController)(as2: Async2Controller)(implicit exec: ExecutionContext, mat: Materializer) extends Controller {

  def index = Action.async { request =>

    val asy1 = as1.index(request)
    val asy2 = as2.index(request)

    for {
      async1Result <- asy1
      async2Result <- asy2

      async1Body <- Pagelet.readBody(async1Result)
      async2Body <- Pagelet.readBody(async2Result)

    } yield {
      Ok(views.html.home2(async1Body, async2Body))
    }
  }

}
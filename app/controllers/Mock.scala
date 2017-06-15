package controllers

import javax.inject._

import akka.actor.{ActorRef, ActorSelection, ActorSystem}

import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.concurrent.duration._
import play.api.mvc.{Result, _}

class Mock @Inject() (actorSystem: ActorSystem)(implicit exec: ExecutionContext) extends Controller {

  def mock(serviceName: String) = Action.async { request =>
    serviceName match {
      case "async1" => respond("11", 0.second)
      case "async2" => respond("24", 3.second)
      case "async3" => respond("asy3", 5.second)
      case "stream" => stream("stream me", 500.millisecond)
      case "stream2" => stream("feed you", 2000.millisecond)
    }
  }

  private def respond(data: String, delay: FiniteDuration): Future[Result] = {
    val promise: Promise[Result] = Promise[Result]()
    actorSystem.scheduler.scheduleOnce(delay) { promise.success(Ok(data)) }
    promise.future
  }

  private def stream(data: String, delay: FiniteDuration): Future[Result] = {
    akka.pattern.after(delay, actorSystem.scheduler){Future.successful(Ok(data))}
  }

}
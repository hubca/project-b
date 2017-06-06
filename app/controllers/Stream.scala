package controllers

import javax.inject.Inject

import akka.NotUsed
import play.api.mvc.{Action, Controller}
import akka.actor.{ActorSystem, Cancellable}
import akka.stream.scaladsl.Source
import akka.stream.scaladsl.Source._

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext
import services.ServiceClient

/**
  * Created by sambo on 06/06/2017.
  */
class Stream @Inject() (as1: AsyncController)(as2: Async2Controller)(sc: ServiceClient)(actorSystem: ActorSystem)(implicit exec: ExecutionContext) extends Controller {

  def singleFuture = Action {
    Ok.chunked(single("hello"))
  }

  def singleStream = Action {
    val source: Source[String, Cancellable] = Source.tick(1.second, 2.seconds, "stream me")
    Ok.chunked(source)
  }

  def twoSingles = Action { request =>

    val source1: Source[String, NotUsed] = Source.single("async1")
    val source2: Source[String, NotUsed] = Source.single("async2").initialDelay(3.seconds)
    val merged: Source[String, NotUsed] = source1.merge(source2)

    Ok.chunked(merged)
  }

  def twoFutures = Action { request =>

    val source1: Source[String, NotUsed] = fromFuture(sc.makeServiceCall("async1"))
    val source2: Source[String, NotUsed] = fromFuture(sc.makeServiceCall("async2"))

    Ok.chunked(source1.merge(source2))
  }

  def tests = Action { request =>

    val source1: Source[String, Cancellable] = tick(1.second, 2.seconds, NotUsed).mapAsync(1){ _ ⇒
      sc.makeServiceCall("stream")
    }

    val source2: Source[String, NotUsed] = unfoldAsync(NotUsed) { _ ⇒
      sc.makeServiceCall("stream2").map(x ⇒ Some(NotUsed → x))
    }

    val source3: Source[String, NotUsed] = Source.unfoldAsync(NotUsed) { NotUsed ⇒
      val asy1 = as1.index(request)
      asy1.map(str ⇒ Some(NotUsed, asy1.toString))
    }

    Ok.chunked(source1)

  }

}

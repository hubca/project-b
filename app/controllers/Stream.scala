package controllers

import javax.inject.Inject

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}
import play.api.mvc.{Action, Controller}
import play.twirl.api.Html
import akka.NotUsed
import akka.actor.{ActorSystem, Cancellable}
import akka.stream.{Materializer, scaladsl}
import akka.stream.actor.ActorPublisherMessage.Request
import akka.stream.scaladsl.{Flow, Merge, Source}
import akka.stream.scaladsl.Source._
import akka.util.LineNumbers.Result
import services.ServiceClient
import data.Pagelet
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs._
import play.api.libs.json.JsError.toFlatJson

/**
  * Created by sambo on 06/06/2017.
  */

class Stream @Inject() (as1: AsyncController)(as2: Async2Controller)(as3: Async3Controller)(sc: ServiceClient)(actorSystem: ActorSystem)(implicit mat: Materializer, ec: ExecutionContext) extends Controller {

  def singleString = Action {
    Ok.chunked(single("hello"))
  }

  def singleStream = Action {
    val source: Source[String, Cancellable] = Source.tick(1.second, 2.seconds, "stream me")
    Ok.chunked(source)
  }

  // not reliable for time
  def doubleStream = Action {
    val source1: Source[String, Cancellable] = Source.tick(0.second, 2.seconds, "stream me\n")
    val source2: Source[String, Cancellable] = Source.tick(0.second, 4.seconds, "interrupt\n")
    val merged: Source[String, Cancellable] = source1.merge(source2)

    Ok.chunked(merged)
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
    val merged: Source[String, NotUsed] = source1.merge(source2)

    Ok.chunked(merged)
  }


  def oneHtmlFuture = Action { request =>

    val async1 = as1.index(embed = true)(request)
    val async1Html = async1.flatMap(x => Pagelet.readBody(x))
    val source = fromFuture(async1Html)

    Ok.chunked(source)
  }

  def twoHtmlFutures1 = Action { request =>

    val async1 = as1.index(embed = true)(request)
    val async2 = as2.index(embed = true)(request)

    val async1Html = async1.flatMap(x => Pagelet.readBody(x))
    val async2Html = async2.flatMap(x => Pagelet.readBody(x))

    val source1 = fromFuture(async1Html)
    val source2 = fromFuture(async2Html)

    val merged: Source[Html, NotUsed] = source1.merge(source2)
    Ok.chunked(merged)
  }

  def twoHtmlFutures2 = Action { request =>

    val async1 = as1.index(embed = true)(request)
    val async2 = as2.index(embed = true)(request)

    val source =  fromFuture(async1)
      .merge(fromFuture(async2))
      .mapAsyncUnordered(2)(b => Pagelet.readBody(b))

    Ok.chunked(source)
  }

  def twoHtmlFutures3 = Action { request =>

    val async1 = as1.index(embed = true)(request)
    val async2 = as2.index(embed = true)(request)

    val source =  Source(List(async1,async2))
      .mapAsyncUnordered(2)(as => as.flatMap(b => Pagelet.readBody(b)))

    Ok.chunked(source)
  }

  def threeHtmlFutures1 = Action { request =>

    val async1 = as1.index(embed = true)(request)
    val async2 = as2.index(embed = true)(request)
    val async3 = as3.index(embed = true)(request)

    Ok.chunked(Source(List(async1,async2,async3)).mapAsyncUnordered(3)( as => as.flatMap(b => Pagelet.readBody(b)) ))
  }

  /*
  // compilation error: as.index not recognised TODO assign a common supertype with the index method signature, or ascribe a structural type for them
  def twoHtmlFutures2 = Action { request =>
    Ok.chunked(Source(List(as1,as2)).mapAsyncUnordered(2)( as => (as.index(embed = true)(request)).flatMap(b => Pagelet.readBody(b)) ))
  }
    def tests = Action { request =


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
  */
}

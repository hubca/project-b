package data

import akka.stream.Materializer
import play.api.mvc.{Codec, Result}
import play.twirl.api.Html
import scala.concurrent.{ExecutionContext, Future}

object Pagelet {

  def readBody(result: Result)(implicit mat: Materializer, codec: Codec, ec: ExecutionContext): Future[Html] = {
    result.body.consumeData.map(byteString => Html(codec.decode(byteString)))
  }

  def readBody2(result: Result)(implicit mat: Materializer, codec: Codec, ec: ExecutionContext): Future[String] = {
    result.body.consumeData.map(byteString => byteString.toString())
  }
}
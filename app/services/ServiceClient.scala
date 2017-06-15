package services

import javax.inject.Inject
import scala.concurrent.Future
import play.api.libs.ws._
import play.api.libs.concurrent.Execution.Implicits._

/**
  * Created by sambo on 27/05/2017.
  */
class ServiceClient @Inject() (ws: WSClient) {

  def makeServiceCall(serviceName: String): Future[String] = {
    ws.url(s"http://localhost:9000/mock/$serviceName").get().map(_.body)
  }

}
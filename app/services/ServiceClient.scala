package services

import javax.inject.Inject
import scala.concurrent.Future
import play.api.libs.ws._
import play.api.libs.concurrent.Execution.Implicits._

/* Created by bosis on 27/05/2017 */
class ServiceClient @Inject() (ws: WSClient) {

  def makeCall(url: String) = ws.url(url).get()

  def makeServiceCall(serviceName: String): Future[String] = makeCall(s"http://localhost:9000/mock/$serviceName").map(_.body)

}
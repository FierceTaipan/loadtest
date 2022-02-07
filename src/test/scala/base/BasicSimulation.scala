package base

import com.github.phisgr.gatling.grpc.Predef.{grpc, managedChannelBuilder}
import com.github.phisgr.gatling.grpc.protocol.StaticGrpcProtocol
import io.gatling.core.Predef.{Simulation, _}
import io.gatling.http.Predef.http
import io.gatling.http.protocol.HttpProtocolBuilder


class BasicSimulation extends Simulation {
  val httpHostName: String = "gorest.co.in"
  val grpcHostName: String = "photoslibrary.googleapis.com"

  val httpProtocol: HttpProtocolBuilder = http
    .baseUrl(s"https://${httpHostName}/")
    .header("Accept-Encoding", "gzip, deflate, br")
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (X11; Linux i686) AppleWebKit/535.19 (KHTML, like Gecko) " +
      "Ubuntu/12.04 Chromium/18.0.1025.151 Chrome/18.0.1025.151 Safari/535.19")
    .shareConnections //в одно подключение несколько коннектов
    .contentTypeHeader("application/json; charset=UTF-8")
    .disableCaching
    .disableFollowRedirect

  val grpcProtocol: StaticGrpcProtocol = grpc(managedChannelBuilder(s"${grpcHostName}", 443))
}
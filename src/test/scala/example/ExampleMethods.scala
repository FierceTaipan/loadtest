package example

import ch.qos.logback.classic.Level
import com.github.phisgr.gatling.grpc.Predef.{grpc, statusCode}
import com.github.phisgr.gatling.grpc.action.GrpcCallActionBuilder
import io.gatling.core.Predef._
import io.gatling.core.feeder.BatchableFeederBuilder
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import models.CreateUserRequest
import utils.{GsonUtils, tuneLogging}
import com.github.phisgr.gatling.grpc.protocol.GrpcProtocol
import com.github.phisgr.gatling.pb.{EpxrLens, value2ExprUpdatable}
import io.grpc.health.v1.{HealthCheckRequest, HealthCheckResponse, HealthGrpc}
import io.grpc.Status
import com.github.phisgr.gatling.grpc.Predef._


object ExampleMethods {

  tuneLogging(classOf[GrpcProtocol].getName, Level.DEBUG)

  private val auth0Headers = Map(
    "Accept" -> "application/json, text/javascript, */*; q=0.01",
    "Content-Type" -> "application/json",
    "Authorization" -> "Bearer 5f3fdd6e4bfaea8a8c6a6ff72362bad046300d014e0ef2e69ee743a8600a5e89")

  // Methods Http

  val getAllUsers: ChainBuilder = exec(http("getAllUsers")
    .get("public/v1/users")
    .queryParam("access-token", "5f3fdd6e4bfaea8a8c6a6ff72362bad046300d014e0ef2e69ee743a8600a5e89")
    .check(status is 200)
  )

  val genderCsvFeeder: BatchableFeederBuilder[String] = csv("gender.csv").random

  val createUser: ChainBuilder = exec(http("createUser")
    .post("public/v1/users")
    .body(
      StringBody(GsonUtils.toJson(CreateUserRequest(
        email = "testQA@ya.ru",
        name = "NewUser",
        gender = "${gender}",
        status = "active")
      ))
    )
    .headers(auth0Headers)
    .check(status.in(200, 201))
    .check(jsonPath("$..data..id").saveAs("userId"))
  )

  val posts: ChainBuilder = exec(http("posts")
    .get("public/v1/posts")
    .check(status is 200)
  )

  val comments: ChainBuilder = exec(http("comments")
    .get("public/v1/comments")
    .check(status is 200)
  )


  // Methods Grpc

  val checkHealth: GrpcCallActionBuilder[HealthCheckRequest, HealthCheckResponse] =
    grpc("request_grpc")
      .rpc(HealthGrpc.METHOD_CHECK)
      .payload(HealthCheckRequest.defaultInstance.updateExpr(
        _.service :~ ""
      ))
      .check(statusCode is Status.Code.OK)


  // Scenario

  val FirstChain: ChainBuilder =
    group("FirstChain") {
      exec(getAllUsers)
        .feed(genderCsvFeeder)
        .exec(createUser)
    }

  val SecondChain: ChainBuilder =
    group("SecondChain") {
      exec(getAllUsers)
        .exec(posts)
        .exec(comments)
    }

  val ThirdChain: ChainBuilder =
    group("ThirdChain") {
      exec(checkHealth)
    }
}

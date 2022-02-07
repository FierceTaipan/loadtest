package example

import base.BasicSimulation
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration._

trait ExampleInjects extends BasicSimulation {
  def loader(scenario: ScenarioBuilder, scenario2: ScenarioBuilder, scenario3: ScenarioBuilder): SetUp = {

    setUp(
      scenario
        .inject(constantUsersPerSec(1).during(1.seconds)).protocols(httpProtocol),
      scenario2
        .inject(constantUsersPerSec(1).during(1.seconds)).protocols(httpProtocol),
      scenario3
        .inject(constantUsersPerSec(1).during(1.seconds)).protocols(grpcProtocol),
    ).maxDuration(5)

  }
}

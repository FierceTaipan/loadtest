package example

import base.BasicSimulation
import example.ExampleMethods._
import io.gatling.app.Gatling
import io.gatling.core.Predef._
import io.gatling.core.config.GatlingPropertiesBuilder
import io.gatling.core.structure.ScenarioBuilder

object TestExample extends App {
  val props = new GatlingPropertiesBuilder

  props.simulationClass("example.ExampleSimulation")
  Gatling.fromMap(props.build)
}

class ExampleSimulation extends BasicSimulation with ExampleInjects {

  def scenarioOne: ScenarioBuilder = scenario("scenarioOne").exec(FirstChain)
  def scenarioTwo: ScenarioBuilder = scenario("scenarioTwo").exec(SecondChain)
  def scenarioThree: ScenarioBuilder = scenario("scenarioThree").exec(ThirdChain)

  loader(scenarioOne, scenarioTwo, scenarioThree)

}

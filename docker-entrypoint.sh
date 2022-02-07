#!/bin/bash

set -ex

SIMULATION_NAME=${SIMULATION_NAME:-example.ExampleSimulation}

exec java ${JAVA_OPTS} -cp /usr/local/app/bin/Example.jar io.gatling.app.Gatling -s ${SIMULATION_NAME}

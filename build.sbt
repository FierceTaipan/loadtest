import Dependencies._
import sbtassembly.AssemblyKeys.assembly
import scalapb.compiler.Version.{grpcJavaVersion, scalapbVersion, protobufVersion}


lazy val root = (project in file("."))
  .settings(
    inThisBuild(List(
      organization := "com.example",
      //      scalaVersion := "2.12.12",
      scalaVersion := "2.13.6",
      version := "0.1.0-SNAPSHOT"
    )),
    name := "loadTest",
    libraryDependencies ++= gatling
  )

scalacOptions ++= Seq(
  "-encoding", "utf8", // Option and arguments on same line
  "-Xfatal-warnings", // New lines for each options
  "-deprecation",
  "-unchecked",
  "-language:higherKinds",
  "-language:postfixOps",
  "-language:implicitConversions",
  "-language:existentials"
)

//toJson, csv
libraryDependencies += "com.google.code.gson" % "gson" % "2.8.9"
libraryDependencies += "com.github.tototoshi" %% "scala-csv" % "1.3.10"

//protobuf stuff
Test / PB.targets := Seq(
  scalapb.gen(flatPackage = true) -> (Test / sourceManaged).value
)
libraryDependencies += "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf"
libraryDependencies += "io.gatling" % "gatling-netty-util" % "3.7.4"

libraryDependencies ++= Seq(
  "io.grpc" % "grpc-netty" % scalapb.compiler.Version.grpcJavaVersion,
  "com.thesamet.scalapb" %% "scalapb-runtime-grpc" % scalapb.compiler.Version.scalapbVersion
)
libraryDependencies += "com.github.phisgr" % "gatling-grpc" % "0.12.0" % "test,it"
libraryDependencies += "io.grpc" % "grpc-netty" % grpcJavaVersion
libraryDependencies += "io.grpc" % "grpc-protobuf" % grpcJavaVersion
libraryDependencies += "io.grpc" % "grpc-stub" % grpcJavaVersion
libraryDependencies += "io.grpc" % "grpc-testing" % grpcJavaVersion % "test"
libraryDependencies += "io.netty" % "netty-tcnative-boringssl-static" % "2.0.38.Final"
libraryDependencies += "org.mockito" % "mockito-core" % "4.3.1" % "test"
autoScalaLibrary := false

enablePlugins(GatlingPlugin, AssemblyPlugin)

Project.inConfig(Test)(baseAssemblySettings)
assembly / assemblyJarName := "Example.jar"

assembly / mainClass := Some("io.gatling.app.Gatling")

assembly / assemblyMergeStrategy := {
  case PathList("META-INF", xs@_x) => MergeStrategy.discard
  case x => MergeStrategy.first
}
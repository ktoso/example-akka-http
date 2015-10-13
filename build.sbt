import NativePackagerKeys._

enablePlugins(JavaAppPackaging)

name := "akka-http-example"

version := "1.0"

scalaVersion := "2.11.7"

lazy val akkaVersion = "2.3.14"
lazy val akkaStreamVersion = "1.0"

libraryDependencies ++= Seq(
  // akka core
  "com.typesafe.akka" %% "akka-actor"      % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit"    % akkaVersion,
  // akka http
  "com.typesafe.akka" %% "akka-http-experimental"    % akkaStreamVersion,
  "com.typesafe.akka" %% "akka-http-spray-json-experimental" % akkaStreamVersion,
  "com.typesafe.akka" %% "akka-http-xml-experimental" % akkaStreamVersion,
  // scala xml
  "org.scala-lang.modules" %% "scala-xml" % "1.0.3",
  // testing
  "com.typesafe.akka" %% "akka-http-testkit-experimental" % akkaStreamVersion % "test",
  "org.scalatest"     %% "scalatest"       % "2.2.4" % "test",
  "junit"              % "junit"           % "4.12" % "test",
  "com.novocode"       % "junit-interface" % "0.11" % "test"
)

testOptions += Tests.Argument(TestFrameworks.JUnit, "-v")

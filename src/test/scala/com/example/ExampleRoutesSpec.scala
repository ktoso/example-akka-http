package com.example

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{Matchers, WordSpec}

class ExampleRoutesSpec extends WordSpec with Matchers // pick a ScalaTest matchers DSL
  with ScalatestRouteTest // provides the Akka HTTP Route Testing DSL
  with ExampleRoutes { // include our to-be-tested routes

  "TestServer's routes" should {

    "show hello world" in {
      // The Route Testing DSL reads as:
      // "given such request passed into the `testRoutes`..."
      // "...the following checks should pass"
      Get("/") ~> testRoutes ~> check {
        status shouldEqual StatusCodes.OK
        responseAs[String] should include ("Hello world!")
      }
    }

    "reply to ping" in {
      Get("/ping") ~> testRoutes ~> check {
        status shouldEqual StatusCodes.OK
        responseAs[String] shouldEqual "PONG!"
      }
    }

    "handle internal failure nicely" in {
      Get("/crash") ~> testRoutes ~> check {
        status shouldEqual StatusCodes.InternalServerError
        responseAs[String] shouldEqual "There was an internal server error."
      }
    }

    "respond with a streaing response" in {
      Get("/stream") ~> testRoutes ~> check {
        status shouldEqual StatusCodes.OK
        // collect the streaming response into a complete string
        responseAs[String] shouldEqual ("hello \n" * 10)
      }
    }
  }
}
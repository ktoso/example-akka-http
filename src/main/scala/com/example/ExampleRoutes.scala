package com.example

import akka.actor.ActorSystem
import akka.http.scaladsl.marshallers.xml.ScalaXmlSupport
import akka.http.scaladsl.model.HttpEntity.Chunked
import akka.http.scaladsl.model.{ContentTypes, HttpResponse}
import akka.http.scaladsl.server.Directives
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source
import akka.util.ByteString

trait ExampleRoutes
  extends Directives // Adds methods for routing DSL
  with ScalaXmlSupport { // Adds support for XML literals (see `index`)

  // require those to be implemented by App
  implicit def system: ActorSystem
  implicit def materializer: ActorMaterializer

  // A route is how we route requests into handling them
  val testRoutes =
    get {
      path("") {
        complete(index)
      } ~
      path("ping") {
        complete("PONG!")
      } ~
      path("crash") {
        complete(sys.error("BOOM!"))
      } ~
      path("stream") {
        // imagine Source is actually from a database or file or other resource:
        val streamingData = Source.repeat("hello \n").take(10).map(ByteString(_))

        // render the response in streaming fashion:
        val dataStream = Chunked.fromData(ContentTypes.`text/plain`, streamingData)
        complete(HttpResponse(entity = dataStream))
      }
    } ~
    pathPrefix("inner")(getFromResourceDirectory("someDir"))

  lazy val index =
    <html>
      <head>
        <title>Hello world!</title>
      </head>
      <body>
        <h1>Say hello to <i>akka-http-core</i>!</h1>
        <p>Defined resources:</p>
        <ul>
          <li><a href="/ping">/ping</a></li>
          <li><a href="/crash">/crash</a></li>
        </ul>
      </body>
    </html>

}

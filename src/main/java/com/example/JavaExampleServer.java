package com.example;

import akka.actor.ActorSystem;
import akka.http.javadsl.model.*;
import akka.http.javadsl.server.HttpApp;
import akka.http.javadsl.server.Route;
import akka.http.javadsl.server.values.Headers;
import akka.http.scaladsl.model.ContentTypes;
import akka.http.scaladsl.model.HttpEntity;
import akka.stream.javadsl.Source;
import akka.util.ByteString;
import scala.runtime.BoxedUnit;

import java.io.IOException;

public class JavaExampleServer extends HttpApp {

  public static void main(String[] args) throws IOException {
    // boot up server using the route as defined below
    ActorSystem system = ActorSystem.create();

    // HttpApp.bindRoute expects a route being provided by HttpApp.createRoute
    new JavaExampleServer().bindRoute("127.0.0.1", 8080, system);
    System.out.println("Type RETURN to exit");
    System.in.read();
    system.shutdown();
  }

  // naive example, would be templating
  final String indexExample = "<html>\n" +
    "      <head>\n" +
    "        <title>Hello world!</title>\n" +
    "      </head>\n" +
    "      <body>\n" +
    "        <h1>Say hello to <i>akka-http-core</i>!</h1>\n" +
    "        <p>Defined resources:</p>\n" +
    "        <ul>\n" +
    "          <li><a href=\"/ping\">/ping</a></li>\n" +
    "          <li><a href=\"/crash\">/crash</a></li>\n" +
    "        </ul>\n" +
    "      </body>\n" +
    "    </html>";


  @Override
  public Route createRoute() {
    return route(
      get(
        pathEndOrSingleSlash().route(complete(indexExample)),
        path("ping").route(complete("PONG!")),
        path("crash").route(complete(fail())),
        path("stream").route(complete(streamingResponse()))
      )
    );
  }

  private HttpResponse streamingResponse() {
    // TODO javadsl getting much more streamlined in 1.1 (released soon)
    final Source<ByteString, Object> data = Source
      .repeat("Hello!")
      .take(5)
      .map(ByteString::fromString)
      .mapMaterializedValue(s -> null); // drop materialized value

    final ContentType ct = ContentType.create(MediaTypes.TEXT_PLAIN);
    final int len = 25; // TODO length not needed in 1.1
    final HttpEntityDefault chunked = HttpEntities.create(ct, len, data);
    return HttpResponse.create().withEntity(chunked);
  }

  String fail() {
    throw new RuntimeException("BOOM!");
  }
}

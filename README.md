Akka HTTP example using Maven and Scala
=======================================

This is an example app showing how to use Akka HTTP with Maven and Scala (and ScalaTest).

Running
-------

You can run this example using

```
$ mvn exec:java
```

Which will run the `com.example.TestServer` application which binds the HTTP server to port 8080.

Testing
-------

Testing is as simple as you'd expect it to be - simply run `mvn test` to run the ScalaTest tests.

Reference docs
--------------

For detailed documentation about Akka HTTP refer to its [reference documentation](http://doc.akka.io/docs/akka-stream-and-http-experimental/1.0/scala.html).

And you may want to read about [testing using ScalaTest](www.scalatest.org/user_guide/using_matchers).

License
-------
Apache 2.0

Konrad 'ktoso' Malawski
package com.dvt.udemy.vertx_starter;

import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.core.Vertx;

public class MainVerticle extends VerticleBase {


  public static void main(String[] args) {
    Vertx vetx = Vertx.vertx();
    vetx.deployVerticle(new MainVerticle()).onSuccess(success -> {
      System.out.printf("Verticle deployed successfully\n");
    }).onFailure(err -> {
      err.printStackTrace();
      System.exit(1);
    });
  }

  @Override
  public Future<?> start() {
    return vertx.createHttpServer().requestHandler(req -> {
      req.response()
        .putHeader("content-type", "text/plain")
        .end("Hello from Vert.x!");
    }).listen(8888).onSuccess(http -> {
      System.out.println("HTTP server started on port 8888");
    });
  }
}

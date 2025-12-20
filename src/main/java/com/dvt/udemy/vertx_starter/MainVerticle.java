package com.dvt.udemy.vertx_starter;

import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainVerticle extends VerticleBase {
  private static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);

  public static void main(String[] args) {
    Vertx vetx = Vertx.vertx();
    vetx.deployVerticle(new MainVerticle()).onSuccess(success -> {
      LOG.debug("Verticle deployed successfully");
    }).onFailure(err -> {
      LOG.error("Verticale failure", err);
      System.exit(1);
    });
  }

  @Override
  public Future<?> start() {
    return vertx.createHttpServer().requestHandler(req -> {
      req.response()
        .putHeader("content-type", "text/plain")
        .end("Hello from Vert.x!");
    }).listen(8080).onSuccess(http -> {
      LOG.info("HTTP server started on port 8888");
      LOG.info("a new message is added");
    });
  }
}

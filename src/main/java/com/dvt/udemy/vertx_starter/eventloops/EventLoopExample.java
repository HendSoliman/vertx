package com.dvt.udemy.vertx_starter.eventloops;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventLoopExample extends AbstractVerticle {

  private static final Logger LOG = LoggerFactory.getLogger(EventLoopExample.class);


  public static void main(String[] args) {
    LOG.info("Starting EventLoopExample");
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new EventLoopExample());
  }

  @Override
  public void start(final Promise<Void> startPromise) throws Exception {
    throw new UnsupportedOperationException("Not implemented yet");
  }
}

package com.dvt.udemy.vertx_starter.eventbuses;

import com.dvt.udemy.vertx_starter.verticals.MainVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventBusExample extends AbstractVerticle {
  public static final String REQUEST_ADDRESS = "request.address";
  private static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);

  public static void main(String[] args) {
    LOG.debug("EventBusExample started");
    final Vertx vertx = Vertx.vertx();

    vertx.deployVerticle(new RequestHandler());
    vertx.deployVerticle(new ResponseHandler());
  }

  public static class RequestHandler extends AbstractVerticle {
    static final String ADDRESS = "my.request.address";

    @Override
    public void start(final Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      var eventBus = vertx.eventBus();
      final String message = "Hello World!";
      LOG.debug("Sending: {}", message);
      eventBus.<String>request(ADDRESS, message).onSuccess(
        reply -> LOG.debug("Response: {}", reply.body())
      ).onFailure(
        err -> LOG.error("Failed to get response: " + err.getMessage())
      );
    }
  }

  public static class ResponseHandler extends AbstractVerticle {
    @Override
    public void start(final Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.eventBus().consumer(RequestHandler.ADDRESS, message -> {
        LOG.debug("Received Message: {}", message.body());
        message.reply("Received your message. Thanks!");
      });


    }

  }

}


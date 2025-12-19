package com.dvt.udemy.vertx_starter.futures;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(VertxExtension.class)
class FuturePromiseExample {

  private static final Logger LOG = LoggerFactory.getLogger(FuturePromiseExample.class);

  @Test
  void promise_success(Vertx vertx, VertxTestContext context) {
    final Promise<String> promise = Promise.promise();
    LOG.debug("Start");
    vertx.setTimer(500, id -> {
      promise.complete("Success");
      LOG.debug("Success");
      context.completeNow();
    });
    LOG.debug("End");
  }

  @Test
  void promise_failure(Vertx vertx, VertxTestContext context) {
    final Promise<String> promise = Promise.promise();
    LOG.debug("Start");
    vertx.setTimer(500, id -> {
      promise.fail(new RuntimeException("Failed!"));
      LOG.debug("Failed");
      context.completeNow();
    });
    LOG.debug("End");
  }

  @Test
  void future_success(Vertx vertx, VertxTestContext context) {
    /*
     * 🕒 Time = 0 ms (method starts)
     * 1️⃣ Promise is created
     * Promise = empty
     * No value yet
     */
    final Promise<String> promise = Promise.promise();
    // 2️⃣ "Start" is logged
    LOG.debug("[{}] Thread Start", Thread.currentThread().getName());

    /*
    3️⃣ Timer is schedule
    vertx.setTimer(500, id -> { ... });
     */
    vertx.setTimer(500, id -> {
      LOG.debug("[{}] About to complete promise", Thread.currentThread().getName());

      //🕒 Time ≈ 500 ms (timer fires)
      //6️⃣ Timer callback runs
      /*
      The promise is completed
      The future becomes successful
      Vert.x immediately:
            1-finds all onSuccess handlers
            2-schedules them to run
       */
      promise.complete("Success");

//      7️⃣ "Timer done." is logged
      LOG.debug("[{}] Timer done", Thread.currentThread().getName());
    });
    /*
    4️⃣ Future is obtained
     This future is linked to the promise
     Still not completed
     */
    final Future<String> future = promise.future();
    /* 5️⃣ Handlers are registered
    You are just saying:
    “When this future completes successfully, do this”
     */
    future
      .onSuccess(result -> {
        LOG.debug("[{}] Result: {}", Thread.currentThread().getName(), result);

        context.completeNow();
      })
      .onFailure(context::failNow);
  }

  @Test
  void future_failure(Vertx vertx, VertxTestContext context) {
    final Promise<String> promise = Promise.promise();
    LOG.debug("Start");
    vertx.setTimer(500, id -> {
      promise.fail(new RuntimeException("Failed!"));
      LOG.debug("Timer done.");
    });
    final Future<String> future = promise.future();
    future
      .onSuccess(context::failNow)
      .onFailure(error -> {
        LOG.debug("Result: ", error);
        context.completeNow();
      });
  }

  @Test
  void future_map(Vertx vertx, VertxTestContext context) {
    final Promise<String> promise = Promise.promise();
    LOG.debug("Start");
    vertx.setTimer(500, id -> {
      promise.complete("Success");
      LOG.debug("Timer done.");
    });
    final Future<String> future = promise.future();
    future
      .map(asString -> {
        LOG.debug("Map String to JsonObject");
        return new JsonObject().put("key", asString);
      })
      .map(jsonObject -> new JsonArray().add(jsonObject))
      .onSuccess(result -> {
        LOG.debug("Result: {} of type {}", result, result.getClass().getSimpleName());
        context.completeNow();
      })
      .onFailure(context::failNow);
  }

  @Test
  void future_coordination(Vertx vertx, VertxTestContext context) {
    vertx.createHttpServer()
      .requestHandler(request -> LOG.debug("{}", request))
      .listen(10_000)
      .compose(server -> {
        LOG.debug("Another task");
        return Future.succeededFuture(server);
      })
      .compose(server -> {
        LOG.debug("Even more");
        return Future.succeededFuture(server);
      })
      .onFailure(context::failNow)
      .onSuccess(server -> {
        LOG.debug("Server started on port {}", server.actualPort());
        context.completeNow();
      });
  }

  @Test
  void future_composition(Vertx vertx, VertxTestContext context) {
    var one = Promise.<Void>promise();
    var two = Promise.<Void>promise();
    var three = Promise.<Void>promise();

    var futureOne = one.future();
    var futureTwo = two.future();
    var futureThree = three.future();

    Future.all(futureOne, futureTwo, futureThree)
      .onFailure(context::failNow)
      .onSuccess(result -> {
        LOG.debug("Success");
        context.completeNow();
      });

    // Complete futures
    vertx.setTimer(500, id -> {
      one.complete();
      two.complete();
      three.complete();
    });
  }

}

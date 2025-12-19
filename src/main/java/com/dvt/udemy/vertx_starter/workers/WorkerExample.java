package com.dvt.udemy.vertx_starter.workers;

import io.vertx.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkerExample extends AbstractVerticle {

  private static final Logger LOG = LoggerFactory.getLogger(WorkerExample.class);

  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.deployVerticle(new WorkerExample());
  }

  @Override
  public void start(final Promise<Void> startPromise) throws Exception {
    vertx.deployVerticle(WorkerVerticle.class.getName(),
      new DeploymentOptions()
        //Previously: .setWorker(true)
        .setThreadingModel(ThreadingModel.WORKER)
        .setWorkerPoolSize(1)
        .setWorkerPoolName("my-worker-verticle")
    );


    executeBlockingCode();
    startPromise.complete();
  }

  private void executeBlockingCode() {
    vertx.executeBlocking(() -> {
        LOG.debug("Executing blocking code");
        Thread.sleep(5000);
        return "OK";
      })
      .onSuccess(res -> LOG.debug("Blocking code Done: {}", res))
      .onFailure(err -> LOG.error("Failed", err));
  }
}

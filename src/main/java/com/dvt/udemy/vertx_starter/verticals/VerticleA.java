package com.dvt.udemy.vertx_starter.verticals;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.vertx.core.AbstractVerticle;

public class VerticleA extends AbstractVerticle {

  private static final Logger LOG = LoggerFactory.getLogger(VerticleA.class);

  @Override
  public void start(final Promise<Void> startPromise) throws Exception {
    LOG.debug("Start {}", getClass().getName());
//    vertx.deployVerticle(new VerticleAA(), whenDeployed -> {
//      LOG.debug("Deployed {}", VerticleAA.class.getName());
//      vertx.undeploy(whenDeployed.result());
//    });

//    vertx.deployVerticle(new VerticleAB(), whenDeployed -> {
//      LOG.debug("Deployed {}", VerticleAB.class.getName());
//      // Do not undeploy
//    });

    // Deploy VerticalAA
    vertx.deployVerticle(VerticleAA.class.getName());
    vertx.deployVerticle(VerticleAB.class.getName());

    startPromise.complete();
  }
}

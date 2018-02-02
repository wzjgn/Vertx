package com.example.demo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

public class StartVerticle extends AbstractVerticle{

	public static void main(String[] args) {


        DeploymentOptions options = new DeploymentOptions().setInstances(2);

	        // 获取vertx基类
	        Vertx vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(40));


	        // 部署发布服务
			vertx.deployVerticle("com.example.demo.RestfulVerticle");
	        vertx.deployVerticle("com.example.demo.EventBusConsumer1");
			vertx.deployVerticle("com.example.demo.EventBusConsumer2");

			vertx.deployVerticle("com.example.demo.BlockVerticle");



	        
	        
	       try {
			Thread.sleep(1000*5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       System.out.println(vertx.deploymentIDs().getClass());
        
    }
 
}

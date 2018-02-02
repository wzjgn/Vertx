package com.example.demo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;

public class BlockVerticle extends AbstractVerticle {


  public void start() {


      EventBus();

      
  }
  
  


    public void EventBus(){


        EventBus eb = vertx.eventBus();
        MessageConsumer<String> consumer = eb.consumer("news.uk.sport");
        consumer.handler(message -> {

            //异步执行耗时操作
            AsynMethod();
            //阻塞执行耗时操作
            // BlockMethod();

            System.out.println("BlockVerticle-I have received a message: " + message.body());
            message.reply("BlockVerticle-how interesting!");
        });

        consumer.completionHandler(res -> {
            if (res.succeeded()) {
                System.out.println("BlockVerticle-The handler registration has reached all nodes");
            } else {
                System.out.println("Registration failed!");
            }
        });
    }


    /**
     * 使用 executeBlocking 方法异步处理阻塞。
     * 当阻塞代码执行完毕的时候，handler将会以异步的方式被回调。
     */
    public void  AsynMethod(){

        System.out.println("blockingMethod() ");

        vertx.executeBlocking(future -> {
            // Call some blocking API that takes a significant amount of time to return

            BlockMethod();
            String result = "block test";
            future.complete(result);
        }, res -> {
            System.out.println("The result is: " + res.result());
        });
    }

    /**
     * 模拟阻塞方法
     */
    public void BlockMethod(){
        try {
            Thread.sleep(1000*10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


} 
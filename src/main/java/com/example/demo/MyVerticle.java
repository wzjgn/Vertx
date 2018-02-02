

            package com.example.demo;
            import io.vertx.core.AbstractVerticle;

            public class MyVerticle extends AbstractVerticle {
             @Override
              public void start() {
                 System.out.println("MyVerticle started!");
              }

             @Override
             public void stop() {
                 System.out.println("MyVerticle stop!");
             }
            }


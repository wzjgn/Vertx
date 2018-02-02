



        package com.example.demo;

        import io.vertx.core.Vertx;
        import io.vertx.core.VertxOptions;

        public class VertStart {
            public static void main(String[] args) {

                Vertx vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(40));

                vertx.deployVerticle(new MyVerticle());

                //vertx.deployVerticle("com.example.demo.MyVerticle");
            }
        }




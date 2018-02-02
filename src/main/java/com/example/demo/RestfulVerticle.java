package com.example.demo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;

public class RestfulVerticle extends AbstractVerticle {

    public void start() {

        // 实例化一个路由器出来，用来路由不同的rest接口
        Router router = Router.router(vertx);
        // 增加一个处理器，将请求的上下文信息，放到RoutingContext中
        router.route().handler(BodyHandler.create());

        // 处理一个post方法的rest接口

        router.post("/post/:param1/:param2").handler(this::handlePost);
        // 处理一个get方法的rest接口
        router.get("/get/:param1/:param2").handler(this::handleGet);
        router.route("/assets/*").handler(StaticHandler.create("assets"));
        // 创建一个httpserver，监听8080端口，并交由路由器分发处理用户请求
        vertx.createHttpServer().requestHandler(router::accept).listen(8080);

    }

    // 处理post请求的handler
    private void handlePost(RoutingContext context) {
        // 从上下文获取请求参数，类似于从httprequest中获取parameter一样
        String param1 = context.request().getParam("param1");
        String param2 = context.request().getParam("param2");

        if (isBlank(param1) || isBlank(param2)) {
            // 如果参数空，交由httpserver提供默认的400错误界面
            context.response().setStatusCode(400).end();
        }

        JsonObject obj = new JsonObject();
        obj.put("method", "post").put("param1", param1).put("param2", param2);

        // 申明response类型为json格式，结束response并且输出json字符串
        context.response().putHeader("content-type", "application/json")
                .end(obj.encodePrettily());
    }

    private void handleGet(RoutingContext context) {

        // 从上下文获取请求参数，类似于从httprequest中获取parameter一样
        String param1 = context.request().getParam("param1");
        String param2 = context.request().getParam("param2");

        // 如果参数空，交由httpserver提供默认的400错误界面
        if(isBlank(param1) || isBlank(param2)) {
            context.response().setStatusCode(400).end();
        }
        JsonObject obj = new JsonObject();
        obj.put("method", "get").put("param1", param1).put("param2", param2);

        context.response().putHeader("content-type", "application/json")
                .end(obj.encodePrettily());
    }

    private boolean isBlank(String str) {
        if(str == null || "".equals(str))
            return true;
        return false;
    }


  public void sendMeg(String param2) {

      EventBus eb = vertx.eventBus();
      eb.send("news.uk.sport", "2---Yay! Someone kicked a ball across a patch of grass", ar -> {
          if(ar.succeeded()) {
              System.out.println("Received reply: " + ar.result().body());
          }
      });
  }




    public void publishMeg(String param2){

        EventBus eb = vertx.eventBus();
        eb.publish("news.uk.sport", "2---Yay! Someone kicked a ball across a patch of grass----");

    }

}
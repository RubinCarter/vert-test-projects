package com.rubin.verttest;

import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

/**
 *
 * @author rubin 2017年12月05日
 */
public class Main {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);
        router.post("/test").handler(content -> {
            HttpServerRequest request = content.request();
            request.setExpectMultipart(true);
            content.next();
        });
        router.post("/test").blockingHandler(content -> {
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            MultiMap multiMap = content.request().formAttributes();
            multiMap.entries().forEach(entry -> {
                System.out.println(entry.getKey() + " " + entry.getValue());
            });
            content.response().end("Hello World");
        });
        server.requestHandler(router::accept).listen(8055);
    }

}

package com.example.controller;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class AppController {
    public static void apiResponse(RoutingContext routingContext){
        //just like arraylist we can add elements in JsonArray
        JsonArray jsonArray=new JsonArray();
        jsonArray.add("c");
        jsonArray.add("java");
        jsonArray.add("javaScript");

        JsonObject jsonObject=new JsonObject();
        jsonObject.put("name","ajay");
        jsonObject.put("message","success");
        jsonObject.put("programmingLangauge",jsonArray);

        routingContext.response().putHeader("content-type","application/json").end(jsonObject.encode());
    }
}

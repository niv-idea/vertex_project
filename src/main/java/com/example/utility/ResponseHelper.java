package com.example.utility;

import com.example.entity.Employee;
import com.example.repository.DbConnection;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.Data;

public class ResponseHelper {
    public static void writeJsonResponse(RoutingContext context, Object response) {
        context.response().putHeader("content-type", "application/json")
                .end(
                        JsonObject.mapFrom(response).put("status", true).encodePrettily()
                );
    }

    public static void writeJsonResponse(RoutingContext context) {
        context.response().putHeader("content-type", "application/json")
                .end(
                        new JsonObject().put("status", true).encodePrettily()
                );
    }

    public static void handleError(RoutingContext context, Object error) {
        context.response().putHeader("content-type", "application/json")
                .end(
                        new JsonObject().put("status", false).put("error", error).encodePrettily()
                );
    }

    public static void handleError(RoutingContext context) {
        context.response().putHeader("content-type", "application/json")
                .end(
                        new JsonObject().put("status", false)
                                .put("error", "something went wrong")
                                .encodePrettily()
                );
    }

}

package com.example;

import com.example.controller.*;
import com.example.repository.DbConnection;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.experimental.Delegate;

import java.lang.module.Configuration;
import java.util.concurrent.CompletableFuture;

public class MainVerticle extends AbstractVerticle {


    @Override
    public void start(Promise<Void> promise) throws Exception {
        //with the help of we can provide the database config in all over project
        ConfigManager.mainConfiguration=config();

        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        httpRouting(router);

        vertx.createHttpServer().requestHandler(router)
                .listen(ConfigManager.mainConfiguration.getInteger("port"), handle -> {
                    if (handle.succeeded()) {
                        System.out.println("Vertical is started on port : "+ConfigManager.mainConfiguration.getInteger("port"));
                        promise.complete();
                    } else {
                        System.out.println("Error in deployed vertical");
                        promise.fail(handle.cause());
                    }
                });
        //db connection initialization
        CompletableFuture.runAsync(()-> {
            DbConnection.initSqlConnection();
        });

    }


    private void httpRouting(Router router) {
        router.get("/check").handler(AppController::apiResponse);
        router.post("/employee/add").handler(AddEmployeeController::handle);
        router.get("/employee/all").handler(GetAllEmployeeController::handler);
        router.delete("/employee/delete").handler(DeleteEmployeeByIdController::handler);
        router.get("/employee/details/:employeeId").handler(GetEmployeeByIdController::handler);
        router.put("/employee/update").handler(UpdateEmployeeController::handle);
        router.post("/customer/add").handler(AddCustomer::handler);
        router.put("/customer/update").handler(UpdateCustomer::handle);
        router.get("/customer/get/:customerId").handler(GetCustomerController::handler);
        router.get("/customer/getAll").handler(GetAllCustomerController::handler);
    }


    public static void main(String[] args) {
        Vertx vertx1=Vertx.vertx();
       // com.example.MainVerticle mainVerticle=new com.example.MainVerticle();
        //no worry he direct pass the object here
        vertx1.deployVerticle(new MainVerticle(),(handle)->{
            if(handle.succeeded()){
                System.out.println("com.example.MainVerticle deployed successfully");
            } else {
                handle.cause().printStackTrace();
            }
        });
        CompletableFuture.runAsync(()-> {
            DbConnection.initSqlConnection();
        });
    }


}

package com.example.controller;

import com.example.entity.Employee;
import com.example.repository.DbConnection;
import com.example.utility.ResponseHelper;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.Data;

public class AddEmployeeController {
    public static void handle(RoutingContext context){
        try{
           // context.request().getParam("id");
            JsonObject requestBody=context.getBodyAsJson();
            Request request=requestBody.mapTo(Request.class);
            createEmployeeAndSave(request);
            ResponseHelper.writeJsonResponse(context);
        }catch (Exception e){
            e.printStackTrace();
            ResponseHelper.handleError(context,e.getMessage());
        }
    }



    private static void createEmployeeAndSave(Request request){
        Employee employee = new Employee();
        employee.setAge(request.age);
        employee.setName(request.name);
        employee.setEmail(request.email);
        employee.setMobileNumber(request.mobileNumber);
        DbConnection.sqlDb.save(employee);
    }



    @Data
    private static class Request{
        private String name;
        private Integer age;
        private String email;
        private String mobileNumber;
    }
}

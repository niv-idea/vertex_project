package com.example.controller;

import com.example.entity.Customer;
import com.example.entity.Employee;
import com.example.repository.DbConnection;
import com.example.utility.ResponseHelper;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.Data;

public enum AddCustomer {
    INSTANCE;
    public static void handler(RoutingContext routingContext){

        try{
            JsonObject requestBody = routingContext.getBodyAsJson();
            CustomerRequest request = requestBody.mapTo(CustomerRequest.class);
            createCustomer(request);
            ResponseHelper.writeJsonResponse(routingContext);
        }catch (Exception e){
            e.printStackTrace();
            ResponseHelper.handleError(routingContext,e.getMessage());
        }

    }



    private static void createCustomer(CustomerRequest request){
        Customer customer=new Customer();
        customer.setName(request.name);
        customer.setMobileNumber(request.mobileNumber);
        customer.setCity(request.city);
        customer.setEmail(request.email);
        DbConnection.sqlDb.save(customer);
    }
    @Data
    private static class CustomerRequest{
        private String name;
        private String mobileNumber;
        private String city;
        private String email;
    }
}

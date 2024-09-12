package com.example.controller;

import com.example.entity.Customer;
import com.example.exception.ResourseException;
import com.example.repository.DbConnection;
import com.example.utility.ResponseHelper;
import io.vertx.ext.web.RoutingContext;
import lombok.Data;

public class UpdateCustomer {
    public static void handle(RoutingContext context){
        Integer id = Integer.valueOf(context.request().getParam("id"));
        CustomerRequest request = context.getBodyAsJson().mapTo(CustomerRequest.class);
         update(request,id);
        ResponseHelper.writeJsonResponse(context);
    }

    private static void update(CustomerRequest request,Integer id){
       Customer customer= DbConnection.sqlDb.find(Customer.class).where().eq("id", id).findOneOrEmpty().orElseThrow(()->new ResourseException("Customer not found"));
        if(request.name!=null){
            customer.setName(request.getName());
        }
        if(request.getMobileNumber()!=null){
            customer.setMobileNumber(request.mobileNumber);
        }
        if(request.getCity()!=null){
            customer.setCity(request.getCity());
        }
        if(request.getEmail()!=null){
            customer.setEmail(request.getEmail());
        }
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

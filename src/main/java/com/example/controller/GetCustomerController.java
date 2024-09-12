package com.example.controller;

import com.example.entity.Customer;
import com.example.repository.DbConnection;
import com.example.utility.ResponseHelper;
import io.vertx.ext.web.RoutingContext;
import lombok.Data;

public class GetCustomerController {
    public static void handler(RoutingContext routingContext){
        try{
            String customerId = routingContext.request().getParam("customerId");
            System.out.println("customerId : " + customerId);
            Integer id = Integer.valueOf(customerId);
            Customer customer = DbConnection.sqlDb.find(Customer.class, id);
            if (customer == null) {
                System.out.println("Customer not available");
            }
            CustomerResponse response=entityToDto(customer);
            ResponseHelper.writeJsonResponse(routingContext, response);

        }catch (Exception e){
            e.printStackTrace();
            ResponseHelper.handleError(routingContext,e.getMessage());
        }
    }
    private static CustomerResponse entityToDto(Customer customer){
        CustomerResponse response=new CustomerResponse();
        response.setId(customer.getId());
        response.setName(customer.getName());
        response.setMobileNumber(customer.getMobileNumber());
        response.setCity(customer.getCity());
        response.setEmail(customer.getEmail());

        return response;
    }

    @Data
    private static class CustomerResponse{
        private Integer id;
        private String name;
        private String mobileNumber;
        private String city;
        private String email;
    }

}

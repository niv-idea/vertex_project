package com.example.controller;

import com.example.entity.Customer;
import com.example.repository.DbConnection;
import com.example.utility.ResponseHelper;
import io.vertx.ext.web.RoutingContext;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class GetAllCustomerController {
    public static void handler(RoutingContext routingContext){
        try{
            FinalResponse finalResponse = new FinalResponse();
            List<CustomerResponse> responses = new ArrayList<>();
            List<Customer> customers = DbConnection.sqlDb.find(Customer.class).findList();

            //now it's time to retrieve
            for (Customer customer : customers) {
                CustomerResponse response = entityToDto(customer);
                responses.add(response);
            }

            finalResponse.setCustomers(responses);
            ResponseHelper.writeJsonResponse(routingContext, finalResponse);
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
    private static class FinalResponse{
       private List<CustomerResponse> customers=new ArrayList<>();
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

package com.example.controller;

import com.example.entity.Employee;
import com.example.repository.DbConnection;
import com.example.utility.ResponseHelper;
import io.vertx.ext.web.RoutingContext;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


public class GetAllEmployeeController {

    public static void handler(RoutingContext routingContext){

        try{
            Response response=new Response();
            List<ResponseDto> responses = new ArrayList<>();
             List<Employee> employees=  DbConnection.sqlDb.find(Employee.class).findList();

            //now retrieve the data one by one
            for (Employee employee : employees) {
                ResponseDto responseDto = entityToDto(employee);
                responses.add(responseDto);
            }

            response.setEmployees(responses);
//            List<ResponseDto> responses = employees.stream()
//                    .map(this::entityToDto)  // Convert each Employee to ResponseDto
//                    .collect(Collectors.toList());
            ResponseHelper.writeJsonResponse(routingContext, response);
        }catch (Exception e){
            e.printStackTrace();
            ResponseHelper.handleError(routingContext,e.getMessage());

        }

    }
    private static ResponseDto entityToDto(Employee employee){
        ResponseDto response=new ResponseDto();
        response.setId(employee.getId());
        response.setName(employee.getName());
        response.setAge(employee.getAge());
        response.setEmail(employee.getEmail());
        response.setMobileNumber(employee.getMobileNumber());

        return response;
    }
     @Data
    private static class Response{
       private List<ResponseDto> employees=new ArrayList<>();
    }
    @Data
    private static class ResponseDto{
        private int id;
        private String name;
        private Integer age;
        private String email;
        private String mobileNumber;

    }

}

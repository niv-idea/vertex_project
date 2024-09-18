package com.example.controller;

import com.example.entity.Employee;
import com.example.exception.ResourseException;
import com.example.repository.DbConnection;
import com.example.utility.ResponseHelper;
import io.vertx.ext.web.RoutingContext;
import lombok.Data;

public enum GetEmployeeByIdController {
    INSTANCE;
    public static void handler(RoutingContext routingContext){
       try {
            String employeeId = routingContext.request().getParam("employeeId");
            System.out.println("empId : " + employeeId);
            Integer id = Integer.valueOf(employeeId);
            Employee employee = DbConnection.sqlDb.find(Employee.class, id);
            //handle here the error which can comes in Runtime exception if it is employee not available by this id

            if (employee == null) {
                throw new ResourseException("Employee not found");
            }
            ResponseDto response = entityToDto(employee);
            ResponseHelper.writeJsonResponse(routingContext, response);
        }catch (Exception e){
            e.printStackTrace();
            ResponseHelper.writeJsonResponse(routingContext,e.getMessage());
       }

    }
    private static ResponseDto entityToDto(Employee employee){
        ResponseDto response=new ResponseDto();
        response.setEmployeeId(employee.getId());
        response.setName(employee.getName());
        if(employee.getAge()!=null && employee.getAge()>0) {
            response.setAge(employee.getAge());
        }
        response.setEmail(employee.getEmail());
        if(employee.getMobileNumber()!=null) {
            response.setMobileNumber(employee.getMobileNumber());
        }
        return response;
    }



    @Data
    private static class ResponseDto{
        private Integer employeeId;
        private String name;
        private Integer age;
        private String email;
        private String mobileNumber;
    }



}

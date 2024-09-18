package com.example.controller;

import com.example.entity.Employee;
import com.example.exception.ResourseException;
import com.example.repository.DbConnection;
import com.example.repository.EmployeeRepository;
import com.example.utility.ResponseHelper;
import io.vertx.ext.web.RoutingContext;

public enum DeleteEmployeeByIdController {
    INSTANCE;
    public static void handler(RoutingContext routingContext){

        try {
            String empId = routingContext.request().getParam("empId");
            System.out.println("empId : " + empId);
            Integer id = Integer.valueOf(empId);
            Employee employee = DbConnection.sqlDb.find(Employee.class, id);
            if (employee == null) {
                throw new ResourseException("this id related Employee not available");
            }
            DbConnection.sqlDb.delete(employee);

            ResponseHelper.writeJsonResponse(routingContext, "Employee details deleted successfully");
        }catch (Exception e){
            e.printStackTrace();
            ResponseHelper.handleError(routingContext,e.getMessage());
        }
    }
}

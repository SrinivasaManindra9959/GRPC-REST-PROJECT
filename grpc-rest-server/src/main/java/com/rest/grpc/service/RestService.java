package com.rest.grpc.service;

import com.rest.grpc.model.Employee;
import org.springframework.stereotype.Service;

@Service
public class RestService {

    public Employee getEmployeeData(Integer id){
        Employee employee = Employee.builder().id(id).name("Srinivas").company("xyz").build();
        return employee;
    }
}

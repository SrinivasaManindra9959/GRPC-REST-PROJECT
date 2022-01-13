package com.rest.grpc.controller;

import com.rest.grpc.model.Employee;
import com.rest.grpc.service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/app")
public class RestGrpcController {

    @Autowired
    private RestService restService;

    @GetMapping(value="/rest/getEmployee",consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee getEmployeeData(@RequestParam(value="id")Integer id){
        return restService.getEmployeeData(id);
    }




}

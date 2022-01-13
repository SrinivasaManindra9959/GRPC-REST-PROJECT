package com.rest.grpc.client.controller;

import com.rest.grpc.client.model.Employee;
import com.rest.grpc.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/main")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping(value="/rest/get",consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee getEmployeeData(@RequestParam("id") Integer id){
        return clientService.getEmployeeData(id);
    }

    @PostMapping(value="/grpc/create",consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee createEmployee(@RequestBody Employee request){
        return clientService.createEmployee(request);
    }

    @GetMapping(value="/grpc/get",consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee getEmployee(){
        return clientService.getEmployee();
    }
}

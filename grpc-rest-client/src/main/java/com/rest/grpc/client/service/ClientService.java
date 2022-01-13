package com.rest.grpc.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import com.rest.grpc.client.model.Employee;
import com.rest.grpc.stub.EmployeeGrpc;
import com.rest.grpc.stub.EmployeeStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.google.protobuf.util.JsonFormat;

import java.util.concurrent.TimeUnit;

@Service
public class ClientService {

    private RestTemplate restTemplate;

    public ClientService(RestTemplate restTemplate)
    {
        this.restTemplate = restTemplate;
    }

    public Employee getEmployeeData(Integer id) {
        try {
            HttpEntity<Employee> httpEntity = new HttpEntity(Employee.class);
            String url = "http://localhost:9000/app/rest/getEmployee?id="+id;
            ResponseEntity<Employee> response = restTemplate.exchange(url, HttpMethod.GET,
                    httpEntity, Employee.class);
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error Occurred " + e.getMessage());
            throw new RuntimeException();
        }
    }
    public Employee createEmployee(Employee request){
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8000).
                usePlaintext().build();
        EmployeeGrpc.EmployeeBlockingStub studentGrpc = EmployeeGrpc.newBlockingStub(channel);

        EmployeeStub.EmployeeRequest.Builder protoReq = EmployeeStub.EmployeeRequest.newBuilder();
        protoReq = this.pojoToProtoBuf(request,protoReq);

        EmployeeStub.EmployeeResponse response = studentGrpc.withDeadlineAfter(5000L, TimeUnit.MILLISECONDS)
                .createEmployee(protoReq.build());
        Employee resp = new Employee();
        resp = this.protoBufToPojo(resp,response);

        channel.shutdownNow();
        return resp;
    }
    public Employee getEmployee(){
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8000).
                usePlaintext().build();
        EmployeeStub.Empty.Builder request = EmployeeStub.Empty.newBuilder();

        EmployeeGrpc.EmployeeBlockingStub studentGrpc = EmployeeGrpc.newBlockingStub(channel);
        EmployeeStub.EmployeeResponse response = studentGrpc.withDeadlineAfter(5000L, TimeUnit.MILLISECONDS)
                .getEmployee(request.build());
        Employee resp = new Employee();
        resp = this.protoBufToPojo(resp,response);

        channel.shutdownNow();
        return resp;
    }

    private EmployeeStub.EmployeeRequest.Builder pojoToProtoBuf(Employee request,EmployeeStub.EmployeeRequest.Builder protoReq){
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString(request);
            JsonFormat.parser().ignoringUnknownFields().merge(json, protoReq);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        return protoReq;
    }

    private Employee protoBufToPojo(Employee response,EmployeeStub.EmployeeResponse protoResponse){
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = JsonFormat.printer()
                    .preservingProtoFieldNames()
                    .includingDefaultValueFields()
                    .omittingInsignificantWhitespace()
                    .print(protoResponse);
            response=  mapper.readValue(jsonString,Employee.class);
        }catch(Exception e){
            e.printStackTrace();
        }
        return response;
    }
}

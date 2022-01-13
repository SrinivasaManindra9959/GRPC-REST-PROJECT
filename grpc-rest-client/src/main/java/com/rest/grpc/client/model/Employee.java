package com.rest.grpc.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Employee {
    private Integer id;
    private String name;
    private String company;
}

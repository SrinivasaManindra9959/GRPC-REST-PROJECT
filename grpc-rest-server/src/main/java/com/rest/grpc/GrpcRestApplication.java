package com.rest.grpc;

import com.rest.grpc.service.GrpcService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@SpringBootApplication
public class GrpcRestApplication {
    public static void main(String[] args) throws IOException, InterruptedException {
            SpringApplication.run(GrpcRestApplication.class,args);
            Server server = ServerBuilder.forPort(8000).addService(new GrpcService()).build();
            server.start();
            System.out.println("Server port "+server.getPort());
            server.awaitTermination();
    }



}

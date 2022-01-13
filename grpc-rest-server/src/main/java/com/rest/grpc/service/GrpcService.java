package com.rest.grpc.service;

import com.rest.grpc.stub.EmployeeGrpc;
import com.rest.grpc.stub.EmployeeStub;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService
public class GrpcService extends EmployeeGrpc.EmployeeImplBase {
    @Override
    public void createEmployee(EmployeeStub.EmployeeRequest request, StreamObserver<EmployeeStub.EmployeeResponse> responseObserver) {
        EmployeeStub.EmployeeResponse response = EmployeeStub.EmployeeResponse.newBuilder().setId(1)
                .setName("Srinivas").setCompany("Xyz").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getEmployee(EmployeeStub.Empty request, StreamObserver<EmployeeStub.EmployeeResponse> responseObserver) {
        EmployeeStub.EmployeeResponse response = EmployeeStub.EmployeeResponse.newBuilder().setId(1)
                .setName("Srinivas").setCompany("Xyz").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}

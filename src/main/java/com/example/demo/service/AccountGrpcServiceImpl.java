package com.example.demo.service;

import com.example.demo.AccountServiceGrpc;
import com.example.demo.GetContactInfoRequest;
import com.example.demo.GetContactInfoResponse;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class AccountGrpcServiceImpl extends AccountServiceGrpc.AccountServiceImplBase {
    private final PhoneNumberService phoneNumberService;

    public AccountGrpcServiceImpl(PhoneNumberService phoneNumberService) {
        this.phoneNumberService = phoneNumberService;
    }

    @Override
    public void getContactInfo(GetContactInfoRequest request,
                               StreamObserver<GetContactInfoResponse> responseObserver) {
        String acc = request.getAccountNumber();

        var phone = phoneNumberService.getPhoneNumberGoodAcc(acc);

        GetContactInfoResponse response = GetContactInfoResponse.newBuilder()
                .setPhone(phone)
                .setAccountNumber(acc)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
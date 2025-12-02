package com.example.demo.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ClientFullInfoWithEmail {
    private String accountNumber;
    private double balance;
    private String phoneNumber;
    private String email;
}

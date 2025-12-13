package com.example.demo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@NoArgsConstructor
@Getter
@Setter
@RedisHash("client-accounts")
public class ClientBalanceFetchDto {
    @Id
    private String accountNumber;
    private double balance;


}
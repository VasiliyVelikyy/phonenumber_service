package com.example.demo.service;

import com.example.demo.domain.ClientInfoDto;
import com.example.demo.repository.ClientBalanceFetcherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientInfoFetchFromRedisService {
    private final ClientBalanceFetcherRepository clientBalanceFetcherRepository;
    private final PhoneNumberService phoneNumberService;

    public ClientInfoDto getInfoFromCache(String accountNumber) {
        var client = clientBalanceFetcherRepository.findById(accountNumber)
                .orElseThrow(() -> new RuntimeException("Acc not found " + accountNumber));
        var phone = phoneNumberService.getPhoneNumberGoodAcc(accountNumber);
        return new ClientInfoDto(client.getAccountNumber(),
                client.getBalance(),
                phone);

    }
}

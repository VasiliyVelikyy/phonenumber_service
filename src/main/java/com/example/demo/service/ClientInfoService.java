package com.example.demo.service;

import com.example.demo.domain.ClientInfoDto;
import com.example.demo.repository.ClientBalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientInfoService {
    private final ClientBalanceRepository clientBalanceRepository;
    private final PhoneNumberService phoneNumberService;

    public ClientInfoDto getInfoFromCache(String accountNumber) {
        var client = clientBalanceRepository.findById(accountNumber)
                .orElseThrow(() -> new RuntimeException("Acc not found " + accountNumber));
        var phone = phoneNumberService.getPhoneNumberGoodAcc(accountNumber);
        return new ClientInfoDto(client.getAccountNumber(),
                client.getBalance(),
                phone);

    }
}

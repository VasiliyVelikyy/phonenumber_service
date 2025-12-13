package com.example.demo.controller;

import com.example.demo.domain.ClientInfoDto;
import com.example.demo.service.ClientInfoFetchFromRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static com.example.demo.utils.Constants.URL_INFO_FROM_CACHE;

@RestController
@RequiredArgsConstructor
public class ClientInfoFetchFromRedisController {
    private final ClientInfoFetchFromRedisService clientInfoFetchFromRedisService;

    @GetMapping(URL_INFO_FROM_CACHE)
    public ResponseEntity<ClientInfoDto> getInfoFromCache(@PathVariable String accountNumber) throws InterruptedException {
        var client = clientInfoFetchFromRedisService.getInfoFromCache(accountNumber);
        if (client != null) {
            return ResponseEntity.ok(client);
        }
        return ResponseEntity.notFound().build();
    }
}

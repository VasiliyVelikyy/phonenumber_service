package com.example.demo.integration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AccountKafkaConsumerService {


    @KafkaListener(topics = "${account.created.topic}",groupId = "phonenumber-account-registry")
    public void listenAccountCreated(String accountNumber) {
        log.info(" Received account number from Kafka: {}", accountNumber);

    }
}
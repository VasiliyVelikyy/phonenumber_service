package com.example.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.demo.utils.Constants.*;
import static com.example.demo.utils.TimeUtils.evaluateExecutionTime;

@Service
@Slf4j
public class PhoneNumberService {
    private static Map<String, String> phoneDb;
    private final AtomicInteger count = new AtomicInteger(0);

    public PhoneNumberService() {
        long startTime = System.nanoTime();
        phoneDb = generatePhoneNumber();
        System.out.println("Сгенерировано " + ACCOUNT_COUNT + " номеров.");
        evaluateExecutionTime(startTime);
    }

    public String findPhoneNumberByAccNum(String accountNumber) throws InterruptedException {
        Thread.sleep(10);
        var elem = phoneDb.get(accountNumber);
        if (elem == null) {
            throw new RuntimeException("phone not found =" + accountNumber);
        }
        return elem;
    }

    private Map<String, String> generatePhoneNumber() {

        Map<String, String> map = new HashMap<>(ACCOUNT_COUNT);
        for (int i = 1; i <= 8; i++) {
            String key = String.format("ACC%03d", i);
            map.put(key, generateRandomPhoneNumber());
        }


        for (int i = 9; i <= ACCOUNT_COUNT; i++) {
            String key = ACCOUNT_GENERATE_PREFIX + i;
            map.put(key, generateRandomPhoneNumber());
        }

        for (int i = 0; i <= ACCOUNT_COUNT_WITH_PROBLEM + 20; i++) {
            String key = ACCOUNT_ERROR_PREFIX + i;
            map.put(key, generateRandomPhoneNumber());
        }

        return Collections.unmodifiableMap(map);
    }

    private static String generateRandomPhoneNumber() {
        // Генерируем случайное количество цифр от 10 до 12
        int length = ThreadLocalRandom.current().nextInt(10, 13);
        StringBuilder sb = new StringBuilder("+");
        for (int i = 0; i < length; i++) {
            sb.append(ThreadLocalRandom.current().nextInt(10));
        }
        return sb.toString();
    }

    public String getPhoneNumberGoodAcc(String accountNumber) {
        return getPhoneNum(accountNumber);
    }

    public String getPhoneNumberBadAcc(String accountNumber) {
        if (accountNumber.contains(ACCOUNT_ERROR_PREFIX)) {
            String message = "Имитация сбоя при получении телефона для счёта " + accountNumber;
            log.error(message);
            throw new RuntimeException(message);
        }

        return getPhoneNum(accountNumber);
    }

    private String getPhoneNum(String accountNumber) {
        String phone;
        try {
            phone = findPhoneNumberByAccNum(accountNumber);

            long delay = 200;
             log.info("PhoneNumberController phone= " + phone + " delay= " + delay + " count" + count.incrementAndGet() + " accNum=" + accountNumber);

            Thread.sleep(delay);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return phone;
    }
}
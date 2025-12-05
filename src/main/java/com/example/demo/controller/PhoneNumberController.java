package com.example.demo.controller;

import com.example.demo.service.PhoneNumberService;
import io.opentelemetry.api.trace.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static com.example.demo.utils.Constants.URL_PHONE_BY_BAD_ACCOUNT;
import static com.example.demo.utils.Constants.URL_PHONE_BY_GOOD_ACCOUNT;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PhoneNumberController {

    private final PhoneNumberService phoneNumberService;
    private final Tracer tracer; // ← внедряем Tracer

/*
//    @GetMapping(URL_PHONE_BY_GOOD_ACCOUNT)
//    public ResponseEntity<String> getPhoneNumber(@PathVariable String accountNumber) throws InterruptedException {
//        // Создаём корневой спан для операции
//        Span span = tracer.spanBuilder("getPhoneNumber")
//                .setAttribute("account.number", accountNumber) // пользовательский атрибут
//                .startSpan();
//
//        try (var scope = span.makeCurrent()) {
//            String phone = phoneNumberService.findPhoneNumberByAccNum(accountNumber);
//
//            if (phone != null) {
//                long delay = 200;
//                log.info("PhoneNumberController phone= {} delay= {} count= {} accNum= {}",
//                        phone, delay, count.incrementAndGet(), accountNumber);
//
//                Thread.sleep(delay);
//
//                span.setAttribute("phone.found", true);
//                span.setAttribute("http.status_code", 200);
//                return ResponseEntity.ok(phone);
//            } else {
//                span.setAttribute("phone.found", false);
//                span.setAttribute("http.status_code", 404);
//                return ResponseEntity.notFound().build();
//            }
//
//        } catch (Exception e) {
//            span.recordException(e); // записываем исключение в трейс
//            span.setStatus(StatusCode.ERROR, e.getMessage());
//            throw e;
//        } finally {
//            span.end(); // обязательно завершаем спан
//        }
//    }

//    @GetMapping(URL_PHONE_BY_BAD_ACCOUNT)
//    public ResponseEntity<String> getPhoneWithProblemNumber(@PathVariable String accountNumber) throws InterruptedException {
//        Span span = tracer.spanBuilder("getPhoneWithProblemNumber")
//                .setAttribute("account.number", accountNumber)
//                .startSpan();
//
//        try (var scope = span.makeCurrent()) {
//
//            if (accountNumber.contains(ACCOUNT_ERROR_PREFIX)) {
//                String message = "Имитация сбоя при получении телефона для счёта " + accountNumber;
//                log.error(message);
//                span.setStatus(StatusCode.ERROR, message);
//                span.recordException(new RuntimeException(message));
//                throw new RuntimeException(message);
//            }
//
//            String phone = phoneNumberService.findPhoneNumberByAccNum(accountNumber);
//            if (phone != null) {
//                long delay = 1L + (long) (Math.random() * 1000);
//                log.info("PhoneNumberController phone= {} delay= {}", phone, delay);
//                Thread.sleep(delay);
//
//                span.setAttribute("phone.found", true);
//                span.setAttribute("http.status_code", 200);
//                return ResponseEntity.ok(phone);
//            } else {
//                span.setAttribute("phone.found", false);
//                span.setAttribute("http.status_code", 404);
//                return ResponseEntity.notFound().build();
//            }
//
//        } catch (Exception e) {
//            span.recordException(e);
//            span.setStatus(StatusCode.ERROR, e.getMessage());
//            throw e;
//        } finally {
//            span.end();
//        }
//    }
//}
*/


    @GetMapping(URL_PHONE_BY_GOOD_ACCOUNT)
    public ResponseEntity<String> getPhoneNumber(@PathVariable String accountNumber) throws InterruptedException {
        var phone = phoneNumberService.getPhoneNumberGoodAcc(accountNumber);
        if (phone != null) {
            return ResponseEntity.ok(phone);
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping(URL_PHONE_BY_BAD_ACCOUNT)
    public ResponseEntity<String> getPhoneWithProblemNumber(@PathVariable String accountNumber) throws InterruptedException {
        var phone = phoneNumberService.getPhoneNumberBadAcc(accountNumber);
        if (phone != null) {
            return ResponseEntity.ok(phone);
        }
        return ResponseEntity.notFound().build();
    }

}
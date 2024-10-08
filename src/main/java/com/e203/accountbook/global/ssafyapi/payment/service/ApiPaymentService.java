package com.e203.accountbook.global.ssafyapi.payment.service;

import com.e203.accountbook.global.config.ApiConfig;
import com.e203.accountbook.global.ssafyapi.RequestHeader;
import com.e203.accountbook.global.ssafyapi.payment.dto.account.ApiAccountPaymentRequest;
import com.e203.accountbook.global.ssafyapi.payment.dto.account.ApiAccountPaymentResponse;
import com.e203.accountbook.global.ssafyapi.payment.dto.card.ApiCardPaymentRequest;
import com.e203.accountbook.global.ssafyapi.payment.dto.card.ApiCardPaymentResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApiPaymentService {
    private final WebClient webClient;
    private final ApiConfig apiConfig;
    private final ObjectMapper objectMapper;

    public ApiAccountPaymentResponse getAccountPayment(ApiAccountPaymentRequest request,
                                                       String userKey) {
        log.info("계좌 내역 가져오기 시작");
        LocalDateTime now = LocalDateTime.now();

        RequestHeader header = RequestHeader.builder()
                .apiKey(apiConfig.getApiKey())
                .apiName("inquireTransactionHistoryList")
                .apiServiceCode("inquireTransactionHistoryList")
                .transmissionDate(now.format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                .transmissionTime(now.format(DateTimeFormatter.ofPattern("HHmmss")))
                .institutionCode(apiConfig.getInstitutionCode())
                .fintechAppNo(apiConfig.getFintechAppNo())
                .institutionTransactionUniqueNo(now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSSSS")))
//                .userKey("f8fef7ab-71e7-479a-a98e-a48e6b36a4bb")
                .userKey(userKey)
                .build();

        request.setRequestHeader(header);
        log.info("request : {} " , request);
        String apiUserResponse = "";
        try{
            apiUserResponse = webClient.post()
                .uri("/edu/demandDeposit/inquireTransactionHistoryList")
                .bodyValue(request)
                .exchangeToMono(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        log.info("연결 잘 됨");
                        return response.bodyToMono(String.class);
                    } else {
                        log.info("에러코드 : {}", response.statusCode());
                        log.info("header: {}", response.request().getHeaders());
                        log.info("uri: {}", response.request().getURI());

                        return response.bodyToMono(String.class);
                    }
                }).block();

                log.info("Response: {}", objectMapper.readValue(apiUserResponse, ApiAccountPaymentResponse.class));

            return objectMapper.readValue(apiUserResponse, ApiAccountPaymentResponse.class);
            }catch(Exception e){
                e.printStackTrace();
                log.info("apiUserResponse: {}", apiUserResponse);
                return null;
        }
    }

    public ApiCardPaymentResponse getCardPayment(ApiCardPaymentRequest request,
                                                 String userKey) {
        LocalDateTime now = LocalDateTime.now();

        RequestHeader header = RequestHeader.builder()
                .apiKey(apiConfig.getApiKey())
                .apiName("inquireCreditCardTransactionList")
                .apiServiceCode("inquireCreditCardTransactionList")
                .transmissionDate(now.format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                .transmissionTime(now.format(DateTimeFormatter.ofPattern("HHmmss")))
                .institutionCode(apiConfig.getInstitutionCode())
                .fintechAppNo(apiConfig.getFintechAppNo())
                .institutionTransactionUniqueNo(now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSSSS")))
//                .userKey("f8fef7ab-71e7-479a-a98e-a48e6b36a4bb")
                .userKey(userKey)
                .build();

        request.setHeader(header);

        String apiUserResponse = "";
        try{
            apiUserResponse = webClient.post()
                    .uri("/edu/creditCard/inquireCreditCardTransactionList")
                    .bodyValue(request)
                    .exchangeToMono(response -> {
                        if (response.statusCode().is2xxSuccessful()) {
                            log.info("연결 잘 됨");
                            return response.bodyToMono(String.class);
                        } else {
                            log.info("에러코드 : {}", response.statusCode());
                            log.info("header: {}", response.request().getHeaders());
                            log.info("uri: {}", response.request().getURI());

                            return response.bodyToMono(String.class);
                        }
                    }).block();

            log.info("apiUserResponse : {}", apiUserResponse);
            log.info("Response: {}", objectMapper.readValue(apiUserResponse, ApiCardPaymentResponse.class));

            return objectMapper.readValue(apiUserResponse, ApiCardPaymentResponse.class);
        }catch(Exception e){
            e.printStackTrace();
            log.info("apiUserResponse: {}", apiUserResponse);
            return null;
        }
    }
}

//package com.e203.accountBook.global.ssafyapi.card.service;
//
//import com.e203.accountBook.global.config.ApiConfig;
//import com.e203.accountBook.global.ssafyapi.RequestHeader;
//import com.e203.accountBook.global.ssafyapi.card.dto.ApiCardResponse;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class ApiCardService {
//    private final WebClient webClient;
//    private final ApiConfig apiConfig;
//    private final ObjectMapper objectMapper;
//
//    /**
//     * parameter로 userKey를 받아오던가, userDetail에서 꺼내 써야함.
//     * @return
//     */
//    public ApiCardResponse getCards() {
//        LocalDateTime now = LocalDateTime.now();
//
//        RequestHeader header = RequestHeader.builder()
//                .apiKey(apiConfig.getApiKey())
//                .apiName("inquireSignUpCreditCardList")
//                .apiServiceCode("inquireSignUpCreditCardList")
//                .transmissionDate(now.format(DateTimeFormatter.ofPattern("yyyyMMdd")))
//                .transmissionTime(now.format(DateTimeFormatter.ofPattern("HHmmss")))
//                .institutionCode(apiConfig.getInstitutionCode())
//                .fintechAppNo(apiConfig.getFintechAppNo())
//                .institutionTransactionUniqueNo(now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSSSS")))
//                .userKey(AuthUtil.getCustomUserDetails().getUserKey())
////                .userKey(AuthUtil.getCustomUserDetails().getUserKey   );
//                .build();
//        log.info("userKey : {}", AuthUtil.getCustomUserDetails().getUserKey());
//
//        Map<String, RequestHeader> bodyMap = new HashMap<>();
//        bodyMap.put("Header", header);
//        String apiUserResponse = "";
//        try{
//            apiUserResponse = webClient.post()
//                .uri("/edu/creditCard/inquireSignUpCreditCardList")
//                .bodyValue(bodyMap)
//                .exchangeToMono(response -> {
//                    if (response.statusCode().is2xxSuccessful()) {
//                        log.info("연결 잘 됨");
//                        return response.bodyToMono(String.class);
//                    } else {
//                        log.info("에러코드 : {}", response.statusCode());
//                        log.info("header: {}", response.request().getHeaders());
//                        log.info("uri: {}", response.request().getURI());
//
//
//                        return response.bodyToMono(String.class);
//                    }
//                }).block();
//
//            return objectMapper.readValue(apiUserResponse, ApiCardResponse.class);
//            }catch(Exception e){
//                e.printStackTrace();
//                log.info("apiUserResponse: {}", apiUserResponse);
//                return null;
//        }
//    }
//}

package com.e203.accountbook.global.ssafyapi.payment.dto.card;

import com.e203.accountbook.domain.asset.entity.Card;
import com.e203.accountbook.global.ssafyapi.RequestHeader;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiCardPaymentRequest {
    @JsonProperty("Header")
    private RequestHeader header;
    private String cardNo;
    private String cvc;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
    private LocalDate endDate;


    public static ApiCardPaymentRequest of(Card card, LocalDate startDate){
        return ApiCardPaymentRequest.builder()
                .cardNo(card.getCardNumber())
                .startDate(startDate)
                .endDate(LocalDate.now())
                .cvc(card.getCvc())
                .build();
    }
}

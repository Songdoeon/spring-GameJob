package com.e203.accountbook.global.ssafyapi.payment.dto.card;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiCardRec {
    private String cardIssuerCode;
    private String cardIssuerName;
    private String cardName;
    private String cardNo;
    private String cvc;
    private String estimatedBalance;
    @JsonProperty("transactionList")
    private List<MyPaymentCardDto> transactionList;
}

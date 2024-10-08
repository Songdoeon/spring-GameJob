package com.e203.accountbook.global.ssafyapi.payment.dto.card;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MyPaymentCardDto {
    private Long transactionUniqueNo;
    private Long categoryId;
    private String categoryName;
    private Long merchantId;
    private String merchantName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
    private String transactionDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HHmmss")
    private String transactionTime;
    private Long transactionBalance;
    private String cardStatus;
    private String billStatementsYn;
    private String billStatementsStatus;
}

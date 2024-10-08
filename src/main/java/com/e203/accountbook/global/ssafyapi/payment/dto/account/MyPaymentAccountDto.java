package com.e203.accountbook.global.ssafyapi.payment.dto.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MyPaymentAccountDto {
    private Long transactionUniqueNo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
    private String transactionDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HHmmss")
    private String transactionTime;
    private String transactionType;
    private String transactionTypeName;
    private String transactionAccountNo;
    private Long transactionBalance;
    private Long transactionAfterBalance;
    private String transactionSummary;
    private String transactionMemo;
    private Long assetId;
}

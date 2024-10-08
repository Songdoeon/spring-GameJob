package com.e203.accountbook.global.ssafyapi.payment.dto.account;

import com.e203.accountbook.domain.asset.entity.Account;
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
public class ApiAccountPaymentRequest {
    @JsonProperty("Header")
    private RequestHeader requestHeader;
    private String accountNo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
    private LocalDate endDate;
    private Character transactionType;
    private String orderByType;

    public static ApiAccountPaymentRequest of(Account account, LocalDate startDate){
        return ApiAccountPaymentRequest.builder()
                .accountNo(account.getAccountNumber())
                .startDate(startDate)
                .endDate(LocalDate.now())
                .transactionType('A')
                .orderByType("DESC")
                .build();
    }
}

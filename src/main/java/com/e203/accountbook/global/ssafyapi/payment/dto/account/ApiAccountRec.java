package com.e203.accountbook.global.ssafyapi.payment.dto.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiAccountRec {
    private Long totalCount;
    private List<MyPaymentAccountDto> list;
}

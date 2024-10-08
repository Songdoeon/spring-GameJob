package com.e203.accountbook.domain.payment.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentType {
    EXPENSE("출금, 출금(이체)"),
    INCOME("입금, 입금(이체)");

    private final String type;

    public static PaymentType fromString(String source) {
        switch (source) {
            case "출금":
            case "출금(이체)":
                return EXPENSE;
            case "입금":
            case "입금(이체)":
                return INCOME;
            default:
                throw new IllegalArgumentException("Unknown type: " + source);
        }
    }
}

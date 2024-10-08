package com.e203.accountbook.domain.payment.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentState {
    INCLUDE("포함"),
    EXCLUDE("미포함");

    private final String state;
}

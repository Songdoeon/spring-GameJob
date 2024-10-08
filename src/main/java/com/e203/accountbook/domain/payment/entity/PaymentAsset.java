package com.e203.accountbook.domain.payment.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PaymentAsset {
    CARD("카드"),
    ACCOUNT("계좌");
    private final String name;
}

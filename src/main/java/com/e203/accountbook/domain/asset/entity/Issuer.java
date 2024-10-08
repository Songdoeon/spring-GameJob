package com.e203.accountbook.domain.asset.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Issuer {

    KB국민카드("1001"),
    삼성카드("1002"),
    롯데카드("1003"),
    우리카드("1004"),
    신한카드("1005"),
    현대카드("1006"),
    BC바로카드("1007"),
    NH농협카드("1008"),
    하나카드("1009"),
    IBK기업은행("1010");

    private final String issuerCode;
}

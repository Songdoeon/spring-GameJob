package com.e203.accountbook.domain.asset.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Bank {
    한국은행("001"),
    산업은행("002"),
    기업은행("003"),
    국민은행("004"),
    농협은행("011"),
    우리은행("020"),
    SC제일은행("023"),
    시티은행("027"),
    대구은행("032"),
    광주은행("034"),
    제주은행("035"),
    경남은행("039"),
    새마을금고("045"),
    하나은행("081"),
    신한은행("088"),
    카카오뱅크("090"),
    싸피은행("999");

    private final String bankCode;
}

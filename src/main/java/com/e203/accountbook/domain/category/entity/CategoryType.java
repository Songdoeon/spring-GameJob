package com.e203.accountbook.domain.category.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategoryType {
    DEFAULT("기본"),
    CUSTOM("사용자");

    private final String type;
}

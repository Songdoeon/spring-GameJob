package com.e203.accountbook.global.ssafyapi.card.dto;

import com.e203.accountbook.domain.asset.entity.HideState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApiCardDto {
    private String cardNo;
    private String cvc;
    private String cardIssuerCode;
    private String cardIssuerName;
    private String cardName;
    private HideState state;
}

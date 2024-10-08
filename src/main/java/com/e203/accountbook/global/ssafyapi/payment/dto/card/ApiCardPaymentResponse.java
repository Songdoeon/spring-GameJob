package com.e203.accountbook.global.ssafyapi.payment.dto.card;

import com.e203.accountbook.global.common.ResponseHeader;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiCardPaymentResponse {
    @JsonProperty("Header")
    private ResponseHeader header;
    @JsonProperty("REC")
    private ApiCardRec rec;
    private String responseCode;
    private String responseMessage;
}

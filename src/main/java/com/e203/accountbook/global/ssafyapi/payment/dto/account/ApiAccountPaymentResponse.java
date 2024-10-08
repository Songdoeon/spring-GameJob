package com.e203.accountbook.global.ssafyapi.payment.dto.account;

import com.e203.accountbook.global.common.ResponseHeader;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiAccountPaymentResponse {
    @JsonProperty("Header")
    private ResponseHeader header;
    @JsonProperty("REC")
    private ApiAccountRec rec;
    private String responseCode;
    private String responseMessage;
}


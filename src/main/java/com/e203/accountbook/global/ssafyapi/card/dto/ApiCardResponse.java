package com.e203.accountbook.global.ssafyapi.card.dto;

import com.e203.accountbook.global.common.ResponseHeader;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiCardResponse {
    @JsonProperty("Header")
    private ResponseHeader header;
    @JsonProperty("REC")
    private List<ApiCardDto> rec;
    private String responseCode;
    private String responseMessage;

}

package com.ivanboyukliev.schoolgradingapp.util;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SuccessfulAuthResponse {

    @JsonProperty("response_msg")
    String responseMessage;

    public SuccessfulAuthResponse(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}

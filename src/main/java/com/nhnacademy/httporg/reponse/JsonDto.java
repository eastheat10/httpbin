package com.nhnacademy.httporg.reponse;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface JsonDto {
    String getResponseBody() throws JsonProcessingException;
}


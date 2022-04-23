package com.nhnacademy.httporg.reponse.json;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface JsonDto {

    String getResponseBody() throws JsonProcessingException;

}


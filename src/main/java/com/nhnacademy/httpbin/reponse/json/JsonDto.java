package com.nhnacademy.httpbin.reponse.json;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface JsonDto {

    String bind() throws JsonProcessingException;

}


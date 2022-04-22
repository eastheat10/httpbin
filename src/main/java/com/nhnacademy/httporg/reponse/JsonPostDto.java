package com.nhnacademy.httporg.reponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Map;

public class JsonPostDto implements JsonDto {


    public JsonPostDto(Map<String, String> requestMap) {

    }

    @Override
    public String getResponseBody() throws JsonProcessingException {
        return null;
    }
}

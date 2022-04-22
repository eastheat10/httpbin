package com.nhnacademy.httporg.reponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Map;

public class JsonGetDto implements JsonDto {

    public JsonGetDto(Map<String, String> requestMap) {

    }

    @Override
    public String getResponseBody() throws JsonProcessingException {
        return null;
    }
}

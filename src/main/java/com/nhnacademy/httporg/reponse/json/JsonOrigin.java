package com.nhnacademy.httporg.reponse.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonOrigin implements JsonDto {

    private final String origin;

    public JsonOrigin(String origin) {
        this.origin = origin;
    }

    @Override
    public String bind() throws JsonProcessingException {
        return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
    }

    public String getOrigin() {
        return origin;
    }
}

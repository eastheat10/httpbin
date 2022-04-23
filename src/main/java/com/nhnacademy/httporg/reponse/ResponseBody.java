package com.nhnacademy.httporg.reponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class ResponseBody {

    private final Map<String, String> requestMap;
    private String responseBody;

    public ResponseBody(Map<String, String> map) {
        requestMap = map;
    }

    public String getResponseBody() throws JsonProcessingException {

        String method = requestMap.get("method");
        JsonDto dto;

        if (method.equals("POST")) {
             dto = new JsonPostDto(requestMap);
        } else {
             dto = new JsonGetDto(requestMap);
        }

        this.responseBody =
            new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(dto);

        return responseBody;
    }

    public int getContentLength() {
        return responseBody.getBytes(StandardCharsets.UTF_8).length;
    }
}

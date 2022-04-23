package com.nhnacademy.httporg.reponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.httporg.reponse.json.JsonDto;
import com.nhnacademy.httporg.reponse.json.JsonGetDto;
import com.nhnacademy.httporg.reponse.json.JsonOrigin;
import com.nhnacademy.httporg.reponse.json.JsonPostDto;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class ResponseBody {

    private final Map<String, String> requestMap;
    private String responseBody;

    public ResponseBody(Map<String, String> map) {requestMap = map;}

    public String getResponseBody() throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        String method = requestMap.get("method");
        JsonDto dto;

        if (method.equals("POST")) {
             dto = new JsonPostDto(requestMap);
        } else {
             dto = new JsonGetDto(requestMap);
            if (requestMap.get("path").equals("/ip")) {
                JsonOrigin origin = new JsonOrigin(requestMap.get("origin"));
                responseBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(origin);
                return responseBody;
            }
        }

        this.responseBody =
            objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dto);

        return responseBody;
    }

    public int getContentLength() {
        return responseBody.getBytes(StandardCharsets.UTF_8).length;
    }
}

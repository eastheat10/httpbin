package com.nhnacademy.httpbin.reponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.httpbin.reponse.json.JsonDto;
import com.nhnacademy.httpbin.reponse.json.JsonGetDto;
import com.nhnacademy.httpbin.reponse.json.JsonOrigin;
import com.nhnacademy.httpbin.reponse.json.JsonPostDto;
import com.nhnacademy.httpbin.utils.StringUtil;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class ResponseBody {

    private final Map<String, String> requestMap;
    private String responseBody;

    public ResponseBody(Map<String, String> map) {
        requestMap = map;
    }

    public String getResponseBody() throws JsonProcessingException {

        String method = requestMap.get(StringUtil.METHOD);
        JsonDto dto;

        if (method.equals("POST")) {
            dto = new JsonPostDto(requestMap);
        } else {
            if (requestMap.get(StringUtil.PATH).equals("/ip")) {
                dto = new JsonOrigin(requestMap.get(StringUtil.ORIGIN));
            } else {
                dto = new JsonGetDto(requestMap);
            }
        }

        this.responseBody = dto.bind();

        return responseBody;
    }

    public int getContentLength() {
        return responseBody.getBytes(StandardCharsets.UTF_8).length;
    }
}

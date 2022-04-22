package com.nhnacademy.httporg.reponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;

public class ResponseBody {

    private static final String CRLF = "\r\n";

    private final Map<String, String> requestMap;
    ObjectMapper objectMapper = new ObjectMapper();

    public ResponseBody(Map<String, String> map) {
        requestMap = map;
    }

    public String makeBody() throws IOException {

        JsonPostDto dto = new JsonPostDto(requestMap);

        String result = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dto);
//        System.out.println(result);

        return result;
    }

}

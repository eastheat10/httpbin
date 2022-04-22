package com.nhnacademy.httporg.reponse;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public interface JsonDto {
    String getResponseBody() throws JsonProcessingException;
}

package com.nhnacademy.httporg.reponse.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.httporg.utils.StringUtil;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class JsonGetDto implements JsonDto {

    private Map<String, String> args;
    private Map<String, String> headers;
    private String origin;
    private String url;

    public JsonGetDto(Map<String, String> request) {
        args = parseArgs(request.get("path"));
        headers = new LinkedHashMap<>();
        origin = request.get("origin");
        url = request.get("Host") + request.get("path");
        for (String requestKey : request.keySet()) {
            headers.put(requestKey, request.get(requestKey));
        }
        dataInit();
    }

    @Override
    public String bind() throws JsonProcessingException {
        return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
    }

    public Map<String, String> getArgs() {
        return args;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getOrigin() {
        return origin;
    }

    public String getUrl() {
        return url;
    }

    private void dataInit() {
        headers.remove(StringUtil.BODY);
        headers.remove(StringUtil.ORIGIN);
        headers.remove(StringUtil.METHOD);
        headers.remove(StringUtil.PATH);
        headers.remove(StringUtil.PROTOCOL);
    }

    private Map<String, String> parseArgs(String path) {
        Map<String, String> args = new LinkedHashMap<>();

        StringTokenizer st = new StringTokenizer(path, "?");
        st.nextToken();
        if (!st.hasMoreTokens()) {
            return args;
        }
        String realPath = st.nextToken();
        StringTokenizer queryString = new StringTokenizer(realPath, "&");

        while (queryString.hasMoreTokens()) {
            StringTokenizer params = new StringTokenizer(queryString.nextToken(), "=");
            String key = params.nextToken();
            String value = params.nextToken();
            args.put(key, value);
        }
        return args;
    }
}

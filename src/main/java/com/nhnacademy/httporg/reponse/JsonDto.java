package com.nhnacademy.httporg.reponse;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class JsonDto {

    private Map<String, String> args;
    private Map<String, String> data;
    private Map<String, String> files;
    private Map<String, String> form;
    private Map<String, String> headers;
    private Map<String, String> json;
    private String origin;
    private String url;

    public Map<String, String> getArgs() {
        return args;
    }

    public Map<String, String> getData() {
        return data;
    }

    public Map<String, String> getFiles() {
        return files;
    }

    public Map<String, String> getForm() {
        return form;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getJson() {
        return json;
    }

    public String getOrigin() {
        return origin;
    }

    public String getUrl() {
        return url;
    }

    public JsonDto(Map<String, String> request) {
        args = parseArgs(request.get("path"));
//        data = request.get("body");
        data = new HashMap<>();
        files = new HashMap<>();
        form = new HashMap<>();
        origin = request.get("origin");
        url = request.get("Host") + request.get("path");
        headers = new HashMap<>();
        for (String requestKey : request.keySet()) {
            headers.put(requestKey, request.get(requestKey));
        }
        json = new HashMap<>();
        dataInit();
    }

    private void dataInit() {
        headers.remove("body");
        headers.remove("origin");
        headers.remove("method");
        headers.remove("path");
        headers.remove("protocol");
    }

    private Map<String, String> parseArgs(String path) {
        Map<String, String> args = new HashMap<>();

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

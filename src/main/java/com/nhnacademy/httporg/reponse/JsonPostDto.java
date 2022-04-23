package com.nhnacademy.httporg.reponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class JsonPostDto implements JsonDto {

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

    public JsonPostDto(Map<String, String> request) {
        args = parseArgs(request.get("path"));
        data = new HashMap<>();
        files = new HashMap<>();
        if (request.get("Content-Disposition") != null) {
            files.put(request.get("Content-Disposition"),
                fileParse(request.get("Content-Disposition")));
            request.remove("Content-Disposition");
        }
        form = new HashMap<>();
        headers = new HashMap<>();
        if (isJson(request)) {
            json = new HashMap<>();
        }
        origin = request.get("origin");
        url = request.get("Host") + request.get("path");
        for (String requestKey : request.keySet()) {
            headers.put(requestKey, request.get(requestKey));
        }
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

//    @Override
//    public String getResponseBody() throws JsonProcessingException {
////        return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
//        return "";
//    }

    private String fileParse(String file) {
        String[] split = file.split("\n");
        String tmp = "";
        for (int i = 1; i < split.length - 1; i++) {
            tmp += split[i] + "\n";
        }
        return tmp;
    }

    private boolean isJson(Map<String, String> request) {
        return (request.get("Content-Type") != null) &&
            (request.get("Content-Type").equals("application/json"));
    }
}

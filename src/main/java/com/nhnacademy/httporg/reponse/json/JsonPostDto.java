package com.nhnacademy.httporg.reponse.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class JsonPostDto implements JsonDto {

    private Map<String, String> args;
    private String data;
    private Map<String, String> files;
    private Map<String, String> form;
    private Map<String, String> headers;
    private Map<String, String> json;
    private String origin;
    private String url;

    public JsonPostDto(Map<String, String> request) {
        args = parseArgs(request.get("path"));
        data = request.get("body");
        files = new LinkedHashMap<>();
        if (request.get("Content-Disposition") != null) {
            files.put(request.get("Content-Disposition"),
                fileParse(request.get("Content-Disposition")));
            request.remove("Content-Disposition");
        }
        form = new LinkedHashMap<>();
        headers = new LinkedHashMap<>();
        if (isJson(request)) {
            json = new LinkedHashMap<>();
            String jsonString = request.get("body");
            StringTokenizer jsonData = new StringTokenizer(jsonString, ":");
            while (jsonData.hasMoreTokens()) {
                String key = jsonData.nextToken();
                String value = jsonData.nextToken();
                json.put(key, value);
            }
        }
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

    public String getData() {
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

    private void dataInit() {
        headers.remove("body");
        headers.remove("origin");
        headers.remove("method");
        headers.remove("path");
        headers.remove("protocol");
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

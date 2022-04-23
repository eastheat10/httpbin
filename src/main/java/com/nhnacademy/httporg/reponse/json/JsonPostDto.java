package com.nhnacademy.httporg.reponse.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.httporg.utils.StringUtil;
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
        args = parseArgs(request.get(StringUtil.PATH));
        data = request.get(StringUtil.BODY);
        files = new LinkedHashMap<>();
        if (request.get(StringUtil.CONTENT_DISPOSITION) != null) {
            files.put(request.get(StringUtil.CONTENT_DISPOSITION),
                fileParse(request.get(StringUtil.CONTENT_DISPOSITION)));
            request.remove(StringUtil.CONTENT_DISPOSITION);
        }
        form = new LinkedHashMap<>();
        headers = new LinkedHashMap<>();
        if (isJson(request)) {
            json = new LinkedHashMap<>();
            parsingJson(request);
        }
        origin = request.get(StringUtil.ORIGIN);
        url = request.get(StringUtil.HOST) + request.get(StringUtil.PATH);
        for (String requestKey : request.keySet()) {
            headers.put(requestKey, request.get(requestKey));
        }
        dataInit();
    }

    private void parsingJson(Map<String, String> request) {
        String jsonParse =
            request.get(StringUtil.BODY).substring(1, request.get(StringUtil.BODY).length() - 1);

        StringTokenizer jsonData = new StringTokenizer(jsonParse, ",");
        while (jsonData.hasMoreTokens()) {
            StringTokenizer jsonList = new StringTokenizer(jsonData.nextToken(), ": ");
            String key = jsonList.nextToken();
            String value = jsonList.nextToken();
            json.put(key.substring(1, key.length() - 1), value.substring(1, value.length() - 1));
        }
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

    private String fileParse(String file) {
        String[] split = file.split("\n");
        String tmp = "";
        for (int i = 1; i < split.length - 1; i++) {
            tmp += split[i] + "\n";
        }
        return tmp;
    }

    private boolean isJson(Map<String, String> request) {
        return (request.get(StringUtil.CONTENT_TYPE) != null) &&
            (request.get(StringUtil.CONTENT_TYPE).equals("application/json"));
    }
}

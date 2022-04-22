package com.nhnacademy.httporg.reponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

public class ResponseBody {

    private static final String CRLF = "\r\n";

    private final Map<String, String> requestMap;
    private final OutputStream out;

    ObjectMapper objectMapper = new ObjectMapper();

    String json = { "msg1": "hello", "msg2": "world" };

    Jsondata jsondata = new Jsondata("",",");


    public ResponseBody(Map<String, String> requestMap, Socket socket) throws IOException {
        this.requestMap = requestMap;
        this.out = socket.getOutputStream();
    }

    public String responseBody() {
        int contentLength = 0;
        StringBuilder sb = new StringBuilder();

        sb.append("Access-Control-Allow-Credentials: true").append(CRLF);
        sb.append(CRLF);

        return sb.toString();
    }
}

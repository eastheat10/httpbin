package com.nhnacademy.httporg.reponse;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class ResponseHeader {

    private static final String CRLF = "\r\n";

    private final Map<String, String> requestMap;
    private final OutputStream out;

    public ResponseHeader(Map<String, String> requestMap, Socket socket) throws IOException {
        this.requestMap = requestMap;
        this.out = socket.getOutputStream();
    }

    public String responseHeader() {

        int contentLength = 0;
        StringBuilder sb = new StringBuilder();

        sb.append("HTTP/1.1 200 OK").append(CRLF);
        sb.append("Date: ").append(LocalDateTime.now()
                                                .atZone(ZoneId.of("GMT"))
                                                .format(DateTimeFormatter.RFC_1123_DATE_TIME)).append(CRLF);
        sb.append("Content-Type: application/json").append(CRLF);
        sb.append("Content-Length: ").append(contentLength).append(CRLF);
        sb.append("Connection: keep-alive").append(CRLF);
        sb.append("Server: simple-httpbin/0.0.1").append(CRLF);
        sb.append("Access-Control-Allow-Origin: *").append(CRLF);
        sb.append("Access-Control-Allow-Credentials: true").append(CRLF);
        sb.append(CRLF);

        return sb.toString();
    }

}

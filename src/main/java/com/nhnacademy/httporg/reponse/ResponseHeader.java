package com.nhnacademy.httporg.reponse;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


public class ResponseHeader {

    private static final String CRLF = "\r\n";

    public String responseHeader(int contentLength) {
        return "HTTP/1.1 200 OK" + CRLF
            + "Date: " + LocalDateTime.now()
                                    .atZone(ZoneId.of("GMT"))
                                    .format(DateTimeFormatter.RFC_1123_DATE_TIME)
            + CRLF
            + "Content-Type: application/json" + CRLF
            + "Content-Length: " + contentLength + CRLF
            + "Connection: keep-alive" + CRLF
            + "Server: simple-httpbin/0.0.1" + CRLF
            + "Access-Control-Allow-Origin: *" + CRLF
            + "Access-Control-Allow-Credentials: true" + CRLF;
    }

}

package com.nhnacademy.httporg;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Request {

    private static final int MAX_SIZE = 4096;

    private final InputStream in;
    private String inputData;

    public Request(Socket socket) throws IOException {
        this.in = socket.getInputStream();
    }

    public void getRequest() throws IOException {
        byte[] bytes = new byte[MAX_SIZE];
        in.read(bytes);
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
            new InputStreamReader(new ByteArrayInputStream(bytes)))) {
            br.lines().forEach(line -> sb.append(line).append(System.lineSeparator()));
        }

        inputData = sb.toString().trim();
        System.out.println(inputData);
        System.out.println("====");
    }
}

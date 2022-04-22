package com.nhnacademy.httporg.request;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Arrays;

public class Request {

    private static final int MAX_SIZE = 4096;

    private final InputStream in;
    private String[] inputData; // index 1: request header, index 2: request body

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

        inputData = sb.toString().trim().split("\r\n\r\n");
        System.out.println(Arrays.toString(inputData));
        System.out.println("====");
    }

    public void parse() {

    }

}

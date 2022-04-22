package com.nhnacademy.httporg.request;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Request {
    private static final int MAX_SIZE = 4096;

    private final InputStream in;
    private String[] inputData; // index 1: request header, index 2: request body
    private final Map<String, String> requestMap = new HashMap<>();

    public Request(Socket socket) throws IOException {
        this.in = socket.getInputStream();
    }

    public Map<String, String> getRequest() throws IOException {
        byte[] bytes = new byte[MAX_SIZE];
        in.read(bytes);
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
            new InputStreamReader(new ByteArrayInputStream(bytes)))) {
            br.lines().forEach(line -> sb.append(line).append(System.lineSeparator()));
        }

        this.inputData = sb.toString().trim().split("\n\n");

        System.out.println(inputData[0]);
        System.out.println("====");
        System.out.println(inputData[1]);

        parse();

        return requestMap;
    }


    private void parse() throws IOException {
        try (BufferedReader br = new BufferedReader(
            new InputStreamReader(
                new ByteArrayInputStream(inputData[0].getBytes(StandardCharsets.UTF_8))))) {
            String input;

            while ((input = br.readLine()) != null) {
                String[] splitHeadByColon = input.split(": ");

                if (splitHeadByColon.length == 1) {
                    StringTokenizer st = new StringTokenizer(splitHeadByColon[0]);
                    requestMap.put("method", st.nextToken());
                    requestMap.put("path", st.nextToken());
                    requestMap.put("protocol", st.nextToken());
                    continue;
                }

                requestMap.put(splitHeadByColon[0], splitHeadByColon[1]);
            }
        }

        requestMap.put("origin", requestMap.get("Host"));
        requestMap.put("body", inputData[0]);
    }
}

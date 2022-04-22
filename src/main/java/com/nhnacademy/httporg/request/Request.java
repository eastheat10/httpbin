package com.nhnacademy.httporg.request;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Request {
    private static final int MAX_SIZE = 4096;

    private final InputStream in;
    private String[] inputData; // index 1: request header, index 2: request body
    private final Map<String, String> requestMap = new HashMap<>();

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

        this.inputData = sb.toString().trim().split("\r\n\r\n");

        parse();

//        System.out.println(Arrays.toString(inputData));
//        System.out.println("====");
    }



    private void parse() throws IOException {
        String[] splitHeadLine = inputData[0].split("\n");

        for (int i = 0; i < splitHeadLine.length; i++) {
            String[] splitHeadByColon = splitHeadLine[i].split(": ");
            String[] splitHeadBySpace = splitHeadLine[i].split(" ");

            if(splitHeadByColon.length == 1) {
                switch (i) {
                    case 0:
                        requestMap.put("method", splitHeadBySpace[0]);
                        break;

                    case 1:
                        requestMap.put("request", splitHeadBySpace[1]);
                        break;

                    case 2:
                        requestMap.put("http", splitHeadBySpace[2]);
                        break;
                }
            }
            else {
                requestMap.put(splitHeadByColon[0], splitHeadByColon[1]);
            }
        }

        requestMap.put("origin", requestMap.get("Host"));
    }
}

package com.nhnacademy.httporg.request;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Request {
    private static final int MAX_SIZE = 4096;
    private static final String CONTENT_TYPE = "Content-Type";

    private final InputStream in;
    private String[] inputData = new String[2]; // index 0: request header, index 1: request body
    private final Map<String, String> requestMap = new HashMap<>();
    private final Socket socket;

    public Request(Socket socket) throws IOException {
        this.in = socket.getInputStream();
        this.socket = socket;
    }

    public Map<String, String> getRequest() throws IOException {
        byte[] bytes = new byte[MAX_SIZE];
        in.read(bytes);

        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
            new InputStreamReader(new ByteArrayInputStream(bytes)))) {
            br.lines().peek(System.out::println).forEach(line -> sb.append(line).append(System.lineSeparator()));
        }

        String[] input = sb.toString().trim().split("\n\n");

        inputData[0] = input[0];

        inputData[1] = "";
        for (int i = 1; i < input.length; i++) {
            inputData[1] += input[i];
        }

        parse();

        if (requestMap.get("method").equals("POST") &&
            requestMap.get(CONTENT_TYPE).startsWith("multipart/form-data;")) {
            parseMultiPart();
        }

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

        String ip = (((InetSocketAddress) socket.getRemoteSocketAddress()).getAddress()).toString()
                                                                                        .replace(
                                                                                            "/",
                                                                                            "");
        requestMap.put("origin", ip);
        requestMap.put("body", inputData[1]);
    }

    private void parseMultiPart() throws IOException {
        try (BufferedReader br = new BufferedReader(
            new InputStreamReader(
                new ByteArrayInputStream(inputData[1].getBytes(StandardCharsets.UTF_8))))) {
            String disposition = null;
            br.readLine();
            String input = br.readLine();
            String[] splitContentDisposition = input.split(": ");
            String[] dispositions = splitContentDisposition[1].split("; ");
            Pattern pattern = Pattern.compile("[\"](.*?)[\"]");
            Matcher matcher = pattern.matcher(dispositions[1]);
            while (matcher.find()) {
                disposition = matcher.group();
            }
            requestMap.put(splitContentDisposition[0], disposition.substring(1, disposition.length() - 1));
        }
    }
}

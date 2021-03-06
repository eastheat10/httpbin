package com.nhnacademy.httpbin.request;

import com.nhnacademy.httpbin.utils.StringUtil;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Request {

    private static final Logger log = LoggerFactory.getLogger(Request.class);

    private static final int MAX_SIZE = 4096;

    private final InputStream in;
    private String[] inputData = new String[2]; // index 0: request header, index 1: request body
    private final Map<String, String> requestMap = new LinkedHashMap<>();
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
            br.lines().forEach(line -> sb.append(line).append(System.lineSeparator()));
        }

        String[] input = sb.toString().trim().split("\n\n");

        inputData[0] = input[0];

        inputData[1] = "";
        for (int i = 1; i < input.length; i++) {
            inputData[1] += input[i] + System.lineSeparator();
        }

        parse();

        if (requestMap.get(StringUtil.METHOD).equals("POST") &&
            requestMap.get(StringUtil.CONTENT_TYPE).startsWith("multipart/form-data;")) {
            parseMultiPart();
        }

        log.info(System.lineSeparator() + inputData[0]);
        log.info(System.lineSeparator() + inputData[1]);

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
                    requestMap.put(StringUtil.METHOD, st.nextToken());
                    requestMap.put(StringUtil.PATH, st.nextToken());
                    requestMap.put(StringUtil.PROTOCOL, st.nextToken());
                    continue;
                }
                requestMap.put(splitHeadByColon[0], splitHeadByColon[1]);
            }
        }

        String ip = (((InetSocketAddress) socket.getRemoteSocketAddress()).getAddress()).toString()
            .replace(
                "/",
                "");
        requestMap.put(StringUtil.ORIGIN, ip);
        requestMap.put(StringUtil.BODY, inputData[1]);
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
            requestMap.put(splitContentDisposition[0],
                disposition.substring(1, disposition.length() - 1));
        }
    }
}

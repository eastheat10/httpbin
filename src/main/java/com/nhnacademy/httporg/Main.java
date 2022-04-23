package com.nhnacademy.httporg;

import com.nhnacademy.httporg.reponse.ResponseBody;
import com.nhnacademy.httporg.reponse.ResponseHeader;
import com.nhnacademy.httporg.request.Request;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket()) {
            InetSocketAddress address = new InetSocketAddress(80);
            serverSocket.bind(address);

            Socket socket = serverSocket.accept();

            Request request = new Request(socket);
            Map<String, String> requestMap = request.getRequest();

            ResponseHeader header = new ResponseHeader();
            ResponseBody body = new ResponseBody(requestMap);

            String responseBody = body.getResponseBody();
            String responseHeader = header.responseHeader(body.getContentLength());

            PrintWriter writer = new PrintWriter(socket.getOutputStream());

            System.out.println(responseHeader);
            System.out.println();
            System.out.println(responseBody);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(responseHeader.getBytes(
                StandardCharsets.UTF_8))))) {
                br.lines().forEach(writer::println);
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(responseBody.getBytes(
                StandardCharsets.UTF_8))))) {
                br.lines().forEach(writer::println);
            }
//
//            writer.println(responseHeader);
//            writer.println();
//            writer.println(responseBody);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

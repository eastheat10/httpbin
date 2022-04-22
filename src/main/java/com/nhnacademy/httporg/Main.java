package com.nhnacademy.httporg;

import com.nhnacademy.httporg.reponse.ResponseBody;
import com.nhnacademy.httporg.reponse.ResponseHeader;
import com.nhnacademy.httporg.request.Request;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
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

            writer.println(responseHeader);
            writer.println();
            writer.println(responseBody);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

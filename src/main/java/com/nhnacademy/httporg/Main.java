package com.nhnacademy.httporg;

import com.nhnacademy.httporg.reponse.*;
import com.nhnacademy.httporg.request.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket()) {
            InetSocketAddress address = new InetSocketAddress(80);
            serverSocket.bind(address);

            Socket socket = serverSocket.accept();

            String ip=(((InetSocketAddress) socket.getRemoteSocketAddress()).getAddress()).toString().replace("/","");
            System.out.println(ip);

            Request request = new Request(socket);
            Map<String, String> requestMap = request.getRequest();

            ResponseHeader header = new ResponseHeader(new HashMap<>(), socket);
            String s = header.responseHeader();
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.write(s.getBytes(StandardCharsets.UTF_8));

            ResponseBody responseBody = new ResponseBody(requestMap);

            System.out.println(responseBody.makeBody());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

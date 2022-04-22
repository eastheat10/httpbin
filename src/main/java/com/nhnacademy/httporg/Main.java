package com.nhnacademy.httporg;

<<<<<<< HEAD
=======
import com.nhnacademy.httporg.reponse.ResponseHeader;
import com.nhnacademy.httporg.request.Request;
import java.io.DataOutputStream;
>>>>>>> 4dbcbf8 (modified jackson library)
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket()) {
            InetSocketAddress address = new InetSocketAddress(80);
            serverSocket.bind(address);

            Socket socket = serverSocket.accept();

            String ip=(((InetSocketAddress) socket.getRemoteSocketAddress()).getAddress()).toString().replace("/","");
            System.out.println(ip);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

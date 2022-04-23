package com.nhnacademy.httpbin.reponse;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

class ResponseHeaderTest {

    ServerSocket serverSocket;
    ResponseHeader header;

    @BeforeEach
    void setUp() throws IOException {
        serverSocket = new ServerSocket();
        InetSocketAddress address = new InetSocketAddress(80);
        serverSocket.bind(address);

        Socket socket = serverSocket.accept();

        header = new ResponseHeader();
    }

    @AfterEach
    void tearDown() throws IOException {
        serverSocket.close();
    }

}
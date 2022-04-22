package com.nhnacademy.httporg.reponse;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ResponseHeaderTest {

    ServerSocket serverSocket;
    ResponseHeader header;

    @BeforeEach
    void setUp() throws IOException {
        serverSocket = new ServerSocket();
        InetSocketAddress address = new InetSocketAddress(80);
        serverSocket.bind(address);

        Socket socket = serverSocket.accept();

        header = new ResponseHeader(new HashMap<>(), socket);
    }

    @AfterEach
    void tearDown() throws IOException {
        serverSocket.close();
    }

    @Test
    void responseHeader() {
        header.responseHeader();
    }

}
package com.company;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;

public class Server {
    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 65500;
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(host, port));
            while (true) {
                try (SocketChannel socketChannel = serverSocketChannel.accept()) {
                    ByteBuffer byteBuffer = ByteBuffer.allocate(2 << 10);
                    while (socketChannel.isConnected()) {
                        int bytesCount = socketChannel.read(byteBuffer);
                        if (bytesCount == -1) {
                            break;
                        }
                        String msg = new String(byteBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8);
                        byteBuffer.clear();
                        socketChannel.write(ByteBuffer.wrap((msg.replaceAll(" ", "")).getBytes(StandardCharsets.UTF_8)));
                    }
                } catch (IOException e) {
                    e.getMessage();
                }
            }
        } catch (IOException e) {
            e.getMessage();
        }
    }
}

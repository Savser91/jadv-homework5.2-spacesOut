package com.company;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 65500;
        Scanner scanner = new Scanner(System.in);
        try {
            InetSocketAddress socketAddress = new InetSocketAddress(host, port);
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(socketAddress);
            ByteBuffer byteBuffer = ByteBuffer.allocate(2 << 10);
            String input;

            while (true) {
                System.out.println("Введите строку с пробелами");
                input = scanner.nextLine();
                if ("end".equals(input)) {
                    break;
                }
                socketChannel.write(ByteBuffer.wrap(input.getBytes(StandardCharsets.UTF_8)));
                int bytesCount = socketChannel.read(byteBuffer);
                System.out.println(new String(byteBuffer.array(), 0, bytesCount,
                        StandardCharsets.UTF_8).trim());
                byteBuffer.clear();
            }
        } catch (IOException e) {
            e.getMessage();
        }
    }
}

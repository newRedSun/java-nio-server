package com.qiao.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class Chapter2 {

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(9999));
        SocketChannel socketChannel = serverSocketChannel.accept();
        System.out.println("Socket accepted:" + socketChannel);
        socketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        SelectionKey key = socketChannel.register(selector, SelectionKey.OP_READ);

        int readReady = selector.selectNow();
        System.out.println("readReady:" + readReady);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int read = socketChannel.read(buffer);
        System.out.println(read);
        System.out.println(buffer.remaining());

    }
}
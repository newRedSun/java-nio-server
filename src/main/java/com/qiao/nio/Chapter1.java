package com.qiao.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class Chapter1 {

    private static final int PORT = 8888;

    private static final int TIMEOUT = 3000;

    private static final int BUF_SIZE = 1024;

    private static void selector() {
        Selector selector = null;
        ServerSocketChannel ssc = null;
        try {
            selector = Selector.open();
            ssc = ServerSocketChannel.open();
            ssc.socket().bind(new InetSocketAddress(PORT));
            ssc.configureBlocking(false);
            ssc.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                if (selector.select(TIMEOUT) == 0) {
                    System.out.println("==");
                    continue;
                }
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isAcceptable()) {
                        handleAccept(key);
                    } else if (key.isReadable()) {
                        handleRead(key);
                    } else if (key.isWritable()) {
                        handleWrite(key);
                    } else if (key.isConnectable()) {
                        System.out.println("isConnectable = true");
                    }
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (selector != null) {
                    selector.close();
                }
                if (ssc != null) {
                    ssc.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private static void handleWrite(SelectionKey key) throws IOException {
        ByteBuffer buf = (ByteBuffer) key.attachment();
        buf.flip();
        SocketChannel socketChannel = (SocketChannel) key.channel();
        while (buf.hasRemaining()) {
            socketChannel.write(buf);
        }
        buf.compact();
    }

    private static void handleRead(SelectionKey key) throws IOException {
        System.out.println("------------------read--------------------");
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer buf = (ByteBuffer) key.attachment();
        int bytesRead = socketChannel.read(buf);
        while (bytesRead > 0) {
            buf.flip();
            while (buf.hasRemaining()) {
                System.out.println((char) buf.get());
            }
            System.out.println();
            buf.clear();
            bytesRead = socketChannel.read(buf);
        }
        if (bytesRead == -1) {
            socketChannel.close();
        }
    }

    private static void handleAccept(SelectionKey key) throws IOException {
        System.out.println("----------accept-----------");
        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = ssc.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocateDirect(BUF_SIZE));
    }

    public static void main(String[] args) throws IOException {
        selector();
    }
}

package com.example.java.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Date:     2018年10月16日 16:00 <br/>
 *
 * @author lcc
 * @see
 * @since
 */
public class ClientSide {

  public static void main(String[] args) throws IOException {
    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
    ByteBuffer byteBuffer = ByteBuffer.wrap("hello world".getBytes());
    SocketChannel socketChannel = SocketChannel.open();
    socketChannel.socket().connect(new InetSocketAddress(8000));
    socketChannel.write(byteBuffer);
    socketChannel.read(readBuffer);
    System.out.println(new String(readBuffer.array()));
    socketChannel.close();
  }
}

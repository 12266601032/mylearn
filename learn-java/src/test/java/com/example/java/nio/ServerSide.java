package com.example.java.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

public class ServerSide {

  public static void main(String[] args) throws IOException, InterruptedException {
    Thread thread = new Thread(new Reactor(8000), "reactor");
    thread.start();
    thread.join();
  }

  static class Reactor implements Runnable {

    //选择器，负责对多个channel进行select，负责对OP_ACCEPT、OP_CONNECT、OP_WRITE、OP_READ操作
    private final Selector selector;
    //socket服务器端的nio channel
    private final ServerSocketChannel serverSocketChannel;

    public Reactor(int port) throws IOException {
      selector = Selector.open();
      serverSocketChannel = ServerSocketChannel.open();
      serverSocketChannel.socket()
          .bind(new InetSocketAddress(port));
      //使用selector必须配置为非阻塞模式
      serverSocketChannel.configureBlocking(false);
      //ServerSocketChannel只能作为SelectionKey.OP_ACCEPT
      SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
      //将acceptor封装附加到selectionKey中，方便select后获取进行后面的处理
      selectionKey.attach(new Acceptor());
    }

    @Override
    public void run() {
      try {
        while (!Thread.interrupted()) {
          //阻塞当前线程，直到有可操作的selectionKey
          selector.select();
          //可能同时有多个需要处理
          Set<SelectionKey> selectedKeys = selector.selectedKeys();
          for (SelectionKey selectedKey : selectedKeys) {
            dispatch(selectedKey);
          }
          selectedKeys.clear();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    private void dispatch(SelectionKey selectedKey) {
      //获取在注册到selector时通过selectedKey.attach(object) 附加的对象，作为下一步的执行器
      Runnable runnable = (Runnable) selectedKey.attachment();
      if (runnable != null) {
        runnable.run();
      }
    }

    class Acceptor implements Runnable {

      @Override
      public void run() {
        try {
          //在向selector中注册时设置的interest set为OP_ACCEPT,因此在这必定是有socket连接建立，可以进行下一步数据接收处理了
          SocketChannel socketChannel = serverSocketChannel.accept();
          if (socketChannel != null) {
            //创建处理器
            new Handler(selector, socketChannel);
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  static class Handler implements Runnable {

    private final SocketChannel socketChannel;
    private final SelectionKey selectionKey;
    private ByteBuffer input = ByteBuffer.allocate(1024);
    private ByteBuffer output = ByteBuffer.allocate(1024);
    private static final int READING = 0, SENDING = 1;
    private int state = READING;

    public Handler(Selector selector, SocketChannel socketChannel) throws IOException {
      this.socketChannel = socketChannel;
      //将接收的socket连接作为非阻塞模式使用
      this.socketChannel.configureBlocking(false);
      //注册到selector中，不对任何事件感兴趣
      this.selectionKey = this.socketChannel.register(selector, SelectionKey.OP_READ);
      this.selectionKey.attach(this);
      //设置为当有数据可读时handler在进行处理
      //this.selectionKey.interestOps(SelectionKey.OP_READ);
      selector.wakeup();
    }

    @Override
    public void run() {
      try {
        if (state == READING) {
          read();
        } else if (state == SENDING) {
          send();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    private void send() throws IOException {
      output.put("success".getBytes());
      output.flip();
      //socket进行写操作
      socketChannel.write(output);
      //同样的，写可能也会多次写（如nio文件读写入socket channel）
      if (outputIsComplete()) {
        selectionKey.cancel();
      }
    }

    private boolean outputIsComplete() {
      return true;
    }

    private void read() throws IOException {
      //读取部分数据，可能不完整，需要多次读取
      socketChannel.read(input);
      //判断是否读取完毕，可以通过发送端约定某个特殊标记
      if (inputIsComplete()) {
        //处理数据
        process();
        //切换状态为响应模式
        state = SENDING;
        //更换interest的操作为 可写（socketChannel为可写状态时触发）
        selectionKey.interestOps(SelectionKey.OP_WRITE);
      }

    }

    private void process() {
      input.flip();
      byte[] bytes = new byte[input.limit()];
      input.get(bytes);
      System.out.println(new String(bytes));
    }

    private boolean inputIsComplete() {
      return true;
    }
  }


}

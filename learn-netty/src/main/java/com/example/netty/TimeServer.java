package com.example.netty;

import com.example.netty.handler.TimeServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TimeServer {

  public static void main(String[] args) throws Exception {
    EventLoopGroup bossGroup = new NioEventLoopGroup();
    EventLoopGroup workerGroup = new NioEventLoopGroup();
    try {
      ServerBootstrap bootstrap = new ServerBootstrap()
          //设置负责监听socket的组和处理数据的组
          .group(bossGroup, workerGroup)
          //设置channel为nio server
          .channel(NioServerSocketChannel.class)
          //定义处理器
          .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
              //当有socket channel建立后 会走这里配置通道然后调用handler处理数据
              //这里没有考虑tcp拆包、粘包处理，因此接收的数据可能不是整好一条数据
              //处理拆包粘包问题可以通过特定的标记表示开始、特定的标记表示结束\r\n，或者读取定长数据
              ch.pipeline().addLast(new TimeServerHandler());
            }
          })
          //最大同时处理多少个请求
          .option(ChannelOption.SO_BACKLOG, 128)
          //设置建立的socket channel参数
          .childOption(ChannelOption.SO_KEEPALIVE, true);
      //创建NioServerSocketChannel然后与localhost:8000端口绑定
      //ServerBootstrapAcceptor负责监听
      final Channel channel = bootstrap.bind(8000)
          //等待channelFuture执行完毕
          .sync()
          //获取刚绑定的channel
          .channel();

      new Thread(() -> {
        try {
          Thread.sleep(5000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        channel.close();
        System.out.println("channel closed");
      }).start();

      //channel关闭的future，当其它线程调用channel.close时结束
      //相当于挂起当前线程
      channel.closeFuture()
          //等待关闭future
          .sync();

    } finally {
      workerGroup.shutdownGracefully();
      bossGroup.shutdownGracefully();
      System.out.println("123");
    }
  }


}

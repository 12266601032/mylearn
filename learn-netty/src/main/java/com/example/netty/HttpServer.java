package com.example.netty;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;

import com.example.netty.handler.TimeServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpMessage;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpVersion;

public class HttpServer {

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
              //当有socket channel建立后 会走这里配置处理
              ch.pipeline().addLast(new HttpServerCodec())
                  .addLast(new HttpMessageHandler());
            }
          })
          //最大同时处理多少个请求
          .option(ChannelOption.SO_BACKLOG, 128)
          //设置建立的socket channel参数
          .childOption(ChannelOption.SO_KEEPALIVE, true);
      //创建NioServerSocketChannel然后与localhost:8000端口绑定
      final Channel channel = bootstrap.bind(8000)
          //等待channelFuture执行完毕
          .sync()
          //获取刚绑定的channel
          .channel();

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

  static class HttpMessageHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
      System.out.println(msg);
      FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
          HttpResponseStatus.OK,
          Unpooled.wrappedBuffer("OK".getBytes()));
      response.headers().set(CONTENT_TYPE, "text/plain")
          .set(CONTENT_LENGTH, response.content().readableBytes());
      response.headers().set(CONNECTION, HttpHeaderValues.KEEP_ALIVE);
      System.out.println(response);
      ctx.write(response);
      ctx.flush();
    }
  }


}

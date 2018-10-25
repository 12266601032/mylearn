package com.example.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeServerHandler extends ChannelInboundHandlerAdapter {

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    final ByteBuf buffer = ctx.alloc().buffer(4);
    buffer.writeLong(System.currentTimeMillis());
    ChannelFuture f = ctx.writeAndFlush(buffer);

    f.addListener(new ChannelFutureListener() {
      @Override
      public void operationComplete(ChannelFuture future) throws Exception {
        assert f == future;
        ctx.close();
      }
    });
  }
}

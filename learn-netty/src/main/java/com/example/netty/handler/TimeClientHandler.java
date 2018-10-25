package com.example.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.Date;

/**
 * Date:     2018年10月19日 15:50 <br/>
 *
 * @author lcc
 * @see
 * @since
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter {

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    ByteBuf m = (ByteBuf) msg; // (1)
    try {
      long currentTimeMillis = m.readLong();
      System.out.println(new Date(currentTimeMillis));
      ctx.close();
    } finally {
      m.release();
    }
  }
}

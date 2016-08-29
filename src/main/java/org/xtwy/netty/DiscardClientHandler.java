package org.xtwy.netty;

import org.xtwy.netty.constants.CommonConstant;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.cors.CorsConfig.Builder;
import io.netty.util.AttributeKey;

public class DiscardClientHandler extends ChannelHandlerAdapter {

	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
//		int req = (int) ctx.channel().attr(AttributeKey.valueOf(CommonConstant.ATTRIBUTE_KEY)).get();
//		System.out.println("请求数据为{}"+req);
//		ByteBuf buf =ctx.alloc().buffer().writeInt(req);
//		ctx.writeAndFlush(buf);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
//		ByteBuf buf = (ByteBuf)msg;
//		StringBuilder builder = new StringBuilder();
//		 while (buf.isReadable()) { // (1)
//			 builder.append((char) buf.readByte());
//	        }
//		
//		ByteBuf buf = (ByteBuf)msg;
//		byte[] req = new byte[buf.readableBytes()];
//		buf.readBytes(req);
//		String body = new String(req,"UTF-8");
		
		 ctx.channel().attr(AttributeKey.valueOf(CommonConstant.ATTRIBUTE_KEY)).set(msg.toString());
//		 ctx.channel().close();
		 ctx.close();
	}
	
	


}

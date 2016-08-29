package org.xtwy.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class DiscardServerHandler extends ChannelHandlerAdapter {
	public static  PooledByteBufAllocator  allocator = new PooledByteBufAllocator();

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		
//		ByteBuf buf = (ByteBuf)msg;
//		byte[] req = new byte[buf.readableBytes()];
//		buf.readBytes(req);
//		String body = new String(req,"UTF-8");
//		int s = 0;
//		 while (buf.isReadable()) { // (1)
//			     s = buf.readInt();
//	             System.out.println(s);
//	        }
       
		
		 
//		ByteBuf wbuf = allocator.buffer().writeBytes(("I have recieve"+msg+"\r\n").getBytes("utf-8"));
//		
		
		System.out.println(msg);
		//处理业务代码
		 String result = "I have recieve"+msg;
		 ctx.writeAndFlush(result);
	}

	

}

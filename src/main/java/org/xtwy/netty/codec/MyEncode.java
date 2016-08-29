package org.xtwy.netty.codec;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.Delimiters;

public class MyEncode extends ByteToMessageCodec<String>{

	@Override
	protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out)
			throws Exception {
		out.writeBytes(msg.getBytes("UTF-8"));
		out.writeBytes(Delimiters.lineDelimiter()[0]);
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		Integer length = in.readInt();
		byte[] response = new byte[length];
		in.skipBytes(4).readBytes(response);
		out.add(response);
	}

}

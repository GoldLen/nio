package org.xtwy.thrift;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TMemoryBuffer;
import org.xtwy.media.Media;

import com.hzins.thrift.demo.ThriftRequest;

public class ThriftServerHandler extends ChannelHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		
		//ByteBuf  转换成一个ThriftRequest
		ByteBuf buf = (ByteBuf)msg;
		TMemoryBuffer buffer = new TMemoryBuffer(1024);
		if(buf.hasArray()){
			buffer.write(buf.array());
		}else{
			byte[] dst = new byte[buf.readableBytes()];
			buf.readBytes(dst);
			buffer.write(dst);
		}
		TProtocol prot = new TBinaryProtocol(buffer);
		ThriftRequest req = new ThriftRequest();
		req.read(prot);
		
		Object resp = Media.execute(req);
		ctx.writeAndFlush(resp);
	}

	
	
}

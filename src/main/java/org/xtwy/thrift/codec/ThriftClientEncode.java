package org.xtwy.thrift.codec;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TMemoryBuffer;

import com.hzins.thrift.demo.ThriftRequest;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.MessageToByteEncoder;

public class ThriftClientEncode  extends MessageToByteEncoder<Object>{
	
	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out)
			throws Exception {
	    ThriftRequest request = (ThriftRequest)msg;
		TMemoryBuffer reqBuffer = new TMemoryBuffer(1024);
		TProtocol reqProt = new TBinaryProtocol(reqBuffer);
		request.write(reqProt);
		out.writeBytes(reqBuffer.getArray());
		out.writeBytes(Delimiters .lineDelimiter()[0]);
		reqBuffer.close();
	}

}

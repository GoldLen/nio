package org.xtwy.oldthrift.codec;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TMemoryBuffer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageEncoder;

public class MyThriftClientEncode extends MessageToByteEncoder<Object>{

	
	
	
	
	
	
//	@Override
//	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out)
//			throws Exception {
//	
//	}
//
//	
//	@Override
//	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
//			List<Object> out) throws Exception {
//		super.decodeLast(ctx, in, out);
////		// TODO Auto-generated method stub
////		Integer length = in.readInt();
////		byte[] response = new byte[length];
////		in.skipBytes(4).readBytes(response);
////		out.add(response);
//	}
//	
	public static byte[] buildThriftBinary(Object returnValue){
		try{
			TMemoryBuffer buffer = new TMemoryBuffer(1024);
			TBinaryProtocol proto = new TBinaryProtocol(buffer);
			Method readObjectMethod = returnValue.getClass().getMethod("write",org.apache.thrift.protocol.TProtocol.class);
			readObjectMethod.invoke(returnValue, proto);
			return buffer.getArray();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
    }




	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out)
			throws Exception {
		out.writeBytes(buildThriftBinary(msg));
		out.writeBytes(Delimiters.lineDelimiter()[0]);
		
	}
}

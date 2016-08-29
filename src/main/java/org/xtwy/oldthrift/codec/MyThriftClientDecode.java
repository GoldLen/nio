package org.xtwy.oldthrift.codec;
//package org.xtwy.thrift.codec;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.handler.codec.ByteToMessageDecoder;
//
//import java.util.List;
//
//import org.apache.thrift.protocol.TBinaryProtocol;
//import org.apache.thrift.transport.TMemoryBuffer;
//
//import com.hzins.thrift.demo.ThriftResponse;
//
//public class MyThriftClientDecode  extends ByteToMessageDecoder{
//	
//	@Override
//	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
//			List<Object> out) throws Exception {
//		TMemoryBuffer t = new TMemoryBuffer(1024);
//		byte[] dst = new byte[in.readableBytes()];
//		in.readBytes(dst, 0, in.readableBytes());
//		t.write(dst);
//	    TBinaryProtocol proto = new TBinaryProtocol(t);
//		ThriftResponse request = new ThriftResponse();
//		request.read(proto);
//		out.add(request);
//	}
//
//}

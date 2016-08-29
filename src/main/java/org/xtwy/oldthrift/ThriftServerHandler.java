package org.xtwy.oldthrift;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.Delimiters;

import java.lang.reflect.Method;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TMemoryBuffer;
import org.xtwy.media.Media;

import com.hzins.thrift.demo.ThriftRequest;

public class ThriftServerHandler extends ChannelHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		ByteBuf in = (ByteBuf) msg;
		TMemoryBuffer t = new TMemoryBuffer(1024);
		byte[] dst = new byte[in.readableBytes()];
		in.readBytes(dst, 0, in.readableBytes());
		t.write(dst);
		TBinaryProtocol proto = new TBinaryProtocol(t);
		ThriftRequest request = new ThriftRequest();
		request.read(proto);

		Object response = Media.execute(request);
		// ctx.writeAndFlush();
		ctx.writeAndFlush(buildThriftBinary(response));
		// ctx.channel().close();
	}

	public static ByteBuf buildThriftBinary(Object returnValue) {
		try {
			TMemoryBuffer buffer = new TMemoryBuffer(1024);
			TBinaryProtocol proto = new TBinaryProtocol(buffer);
			Method readObjectMethod = returnValue.getClass().getMethod("write",
					org.apache.thrift.protocol.TProtocol.class);
			readObjectMethod.invoke(returnValue, proto);
			ByteBuf buf = UnpooledByteBufAllocator.DEFAULT.buffer();
			buf.writeBytes(buffer.getArray());
			buf.writeBytes(Delimiters.lineDelimiter()[0]);
			return buf;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}

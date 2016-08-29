package org.xtwy.thrift;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;

import org.xtwy.thrift.codec.ThriftServerEncode;

public class ThriftChannelInitializer extends ChannelInitializer<SocketChannel>{

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ch.pipeline().addLast(new DelimiterBasedFrameDecoder(Integer.MAX_VALUE,Delimiters .lineDelimiter()[0]));
		ch.pipeline().addLast(new ThriftServerEncode());
		ch.pipeline().addLast(new ThriftServerHandler());	
	}
}

package org.xtwy.netty;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import org.xtwy.netty.codec.MyEncode;

@Sharable
public class MyChannelInitializer extends ChannelInitializer<SocketChannel>{

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ch.pipeline().addLast(new DelimiterBasedFrameDecoder(Integer.MAX_VALUE,Delimiters .lineDelimiter()[0]));
		ch.pipeline().addLast(new StringDecoder());
		ch.pipeline().addLast(new MyEncode());
		ch.pipeline().addLast(new DiscardServerHandler());	
		
	}

}

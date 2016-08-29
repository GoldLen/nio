package org.xtwy.combine;

import org.xtwy.combine.codec.CombineDecoder;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class CombineChannelInitializer extends ChannelInitializer<SocketChannel>{

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
//		ch.pipeline().addLast(new HttpRequestDecoder());
//		ch.pipeline().addLast(new HttpObjectAggregator(65536));
//		ch.pipeline().addLast(new HttpResponseEncoder());
		
		ch.pipeline().addLast(new CombineDecoder());
		
		
		ch.pipeline().addLast("CombineServerHandler",new CombineServerHandler());	

	}
	
	
}

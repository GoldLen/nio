package org.xtwy.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

public class WebsocketChannelInitializer extends ChannelInitializer<SocketChannel>{

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ch.pipeline().addLast(new HttpRequestDecoder());
		ch.pipeline().addLast(new HttpObjectAggregator(65536));
		ch.pipeline().addLast(new HttpResponseEncoder());
//		ch.pipeline().addLast(new WebsocketServerHandler());	
		ch.pipeline().addLast(new WebSocketServerProtocolHandler("/websocket"));
		ch.pipeline().addLast(new ChatRoomHandler());
		
	}
}

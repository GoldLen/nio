package org.xtwy.websocket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

public class WebsocketServerHandler extends ChannelHandlerAdapter {
	private WebSocketServerHandshaker handshaker;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		
		if(msg instanceof FullHttpRequest){
			FullHttpRequest req = (FullHttpRequest)msg;
			if(!req.decoderResult().isSuccess() || !req.headers().get("Upgrade").equals("websocket")){
				//输出一bad响应
				DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST);
				if(response.status().code()!=200){
					ByteBuf buf = Unpooled.copiedBuffer("请求异常",CharsetUtil.UTF_8);
					response.content().writeBytes(buf);
					buf.release();
				}
				ctx.writeAndFlush(response);
			}else{
				WebSocketServerHandshakerFactory   HandshakerFactory = new WebSocketServerHandshakerFactory("ws://localhost:8999/websocket", null, false);
				handshaker = HandshakerFactory.newHandshaker(req);
				if(handshaker ==null){
					WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
				}
				handshaker.handshake(ctx.channel(), req);
			
				
			}
			
		}else if(msg instanceof WebSocketFrame){
			
		   if(msg instanceof CloseWebSocketFrame){
			   handshaker.close(ctx.channel(),(CloseWebSocketFrame) msg);
		   }
		   if(msg instanceof TextWebSocketFrame){
			   String req = ((TextWebSocketFrame)msg).text();
			   ctx.writeAndFlush(new TextWebSocketFrame(req+"服务端返回数据"));
		   }
		   
		
			
		}
		
	}


	
	
	
	
}

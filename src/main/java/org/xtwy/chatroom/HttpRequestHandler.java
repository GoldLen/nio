package org.xtwy.chatroom;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderUtil;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;

public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
	private final String wsUri;
	 private static final File INDEX;
	 
	 // static HTTP request handling operation.
	 static {
	  URL location = HttpRequestHandler.class.getProtectionDomain()
	    .getCodeSource().getLocation();
	  try {
	   String path = location.toURI() + "WebSocketClient.html";
	   path = !path.contains("file:") ? path : path.substring(5);
	   INDEX = new File(path);
	  } catch (URISyntaxException e) {
	   throw new IllegalStateException("Unable to locate WebsocketChatClient.html", e);
	  }
	 }
	 
	 // constructor function call for current class
	 public HttpRequestHandler(String wsUri) {
	  this.wsUri = wsUri;
	 }


	 private static void send100Continue(ChannelHandlerContext ctx) {
	  FullHttpResponse response = new DefaultFullHttpResponse(
	    HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
	  ctx.writeAndFlush(response);
	 }

	 @Override
	 public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
	   throws Exception {
	  Channel incoming = ctx.channel();
	  System.out.println("Client:" + incoming.remoteAddress() + "异常");
	  
	  // 当出现异常就关闭连接
	  cause.printStackTrace();
	  ctx.close();
	 }

	@Override
	protected void messageReceived(ChannelHandlerContext ctx,
			FullHttpRequest request) throws Exception {
		  if (wsUri.equalsIgnoreCase(request.uri())) {
			   ctx.fireChannelRead(request.retain());
			  } else {
			   if (HttpHeaderUtil.is100ContinueExpected(request)) {
			    send100Continue(ctx);
			   }
			   
//			   RandomAccessFile file = new RandomAccessFile(INDEX, "r");
//			   HttpResponse response = new DefaultHttpResponse(
//			     request.protocolVersion(), HttpResponseStatus.OK);
//			   response.headers().set(HttpHeaderNames.CONTENT_TYPE,
//			     "text/html; charset=UTF-8");

//			   boolean keepAlive = HttpHeaderUtil.isKeepAlive(request);
//			   if (keepAlive) {
//			    response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH,  (int)file.length());
//			    response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
//			   }
//			   ctx.write(response);
//
//			   if (ctx.pipeline().get(SslHandler.class) == null) {
//			    ctx.write(new DefaultFileRegion(file.getChannel(), 0, file.length()));
//			   } else {
//			    ctx.write(new ChunkedNioFile(file.getChannel()));
//			   }
//			   ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
//			   if (!keepAlive) {
//			    future.addListener(ChannelFutureListener.CLOSE);
//			   }
//
//			   file.close();
			  }
		
	}
	
	
	 @Override
	 public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		 System.out.println("");
	 }
}

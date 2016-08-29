package org.xtwy.http;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.util.AttributeKey;

import org.xtwy.netty.constants.CommonConstant;

public class HttpClientHandler extends ChannelHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		
		if(msg instanceof HttpResponse){
			HttpResponse httpResponse = (HttpResponse)msg;
			System.out.println(httpResponse.headers().get(HttpHeaderNames.CONTENT_TYPE));
		}
		if(msg instanceof HttpContent){
			HttpContent httpContent = (HttpContent)msg;
			ByteBuf buf = httpContent.content();
			ctx.channel().attr(AttributeKey.valueOf(CommonConstant.ATTRIBUTE_KEY)).set(buf.toString(Charset.forName("UTF-8")));
			ctx.channel().close();
		}
		
	
		
	}

	
	
}

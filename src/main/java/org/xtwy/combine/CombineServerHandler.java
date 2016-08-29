package org.xtwy.combine;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

import org.xtwy.media.Media;
import org.xtwy.util.JsonUtils;

public class CombineServerHandler extends ChannelHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		FullHttpRequest request = (FullHttpRequest) msg;
		ByteBuf buf = request.content();
		String req = buf.toString(Charset.forName("UTF-8"));
		RequestParam requestParam = JsonUtils.jsonToBean(req, RequestParam.class);
		Object resp = Media.execute(requestParam);
		
		String jsonp = JsonUtils.beanToJson(resp); 
		FullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(jsonp.getBytes("UTF-8")));
		httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
		httpResponse.headers().setInt(HttpHeaderNames.CONTENT_LENGTH,httpResponse.content().readableBytes());
		httpResponse.headers().set(HttpHeaderNames.CONNECTION,HttpHeaderValues.KEEP_ALIVE);
	
		 ctx.writeAndFlush(httpResponse);
	}

	
	
}

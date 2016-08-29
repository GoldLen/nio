package org.xtwy.pb;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

import org.xtwy.netty.constants.CommonConstant;
import org.xtwy.pb.protocol.ResponseMsgProbuf.ResponseMsg;

public class PbClientHandler extends ChannelHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		ResponseMsg responseMsg = (ResponseMsg)msg;
		ctx.channel().attr(AttributeKey.valueOf(CommonConstant.ATTRIBUTE_KEY)).set(responseMsg.getResponse());
		ctx.channel().close();
	}

	
	
}

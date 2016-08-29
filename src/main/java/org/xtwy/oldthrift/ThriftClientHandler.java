package org.xtwy.oldthrift;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

import org.xtwy.netty.constants.CommonConstant;

import com.hzins.thrift.demo.ThriftRequest;

public class ThriftClientHandler extends ChannelHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		ctx.channel().attr(AttributeKey.valueOf(CommonConstant.ATTRIBUTE_KEY))
				.set(msg);
		ctx.channel().close();
	}

}

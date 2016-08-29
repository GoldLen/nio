package org.xtwy.pb;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import org.xtwy.media.Media;
import org.xtwy.pb.protocol.RequestMsgProbuf.RequestMsg;

public class PbServerHandler extends ChannelHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		RequestMsg  requestMsg = (RequestMsg)msg;
		String cmd = requestMsg.getCmd();
		System.out.println("cmd"+cmd);
		
//		ByteString buf = requestMsg.getRequestParam();
//		User user = User.parseFrom(buf);
//		System.out.println(user.getUserName());
//		ctx.writeAndFlush(user);
//		Media media=new Media();
		
		Object response = Media.execute(requestMsg);
		ctx.writeAndFlush(response);
	}

	
	
}

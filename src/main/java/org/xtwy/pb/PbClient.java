package org.xtwy.pb;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.util.AttributeKey;

import org.xtwy.netty.constants.CommonConstant;
import org.xtwy.pb.protocol.RequestMsgProbuf.RequestMsg;
import org.xtwy.pb.protocol.ResponseMsgProbuf.ResponseMsg;

import com.google.protobuf.ByteString;

public class PbClient {
	public  static Bootstrap b;
	public static  PooledByteBufAllocator  allocator = new PooledByteBufAllocator();
	static{
		try {
			     EventLoopGroup workerGroup = new NioEventLoopGroup();
			      b = new Bootstrap(); // (1)
				 b.group(workerGroup); // (2)
				 b.channel(NioSocketChannel.class); // (3)
				 b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
				 b.handler(new ChannelInitializer<SocketChannel>() {
				     @Override
				     public void initChannel(SocketChannel ch) throws Exception {
				    	 ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
				    	 ch.pipeline().addLast(new ProtobufDecoder(ResponseMsg.getDefaultInstance()));
						 ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
						 ch.pipeline().addLast(new ProtobufEncoder());
						 ch.pipeline().addLast(new PbClientHandler());
				     }
				 });
				
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	 public static ByteString startClient(RequestMsg.Builder obj) throws Exception {
		     ChannelFuture  f= b.connect("localhost", 8999).sync(); 
		     f.channel().writeAndFlush(obj);
			 f.channel().closeFuture().sync();
			 return (ByteString) f.channel().attr(AttributeKey.valueOf(CommonConstant.ATTRIBUTE_KEY)).get();
		
	 }
	 
	 
	 
	 
}

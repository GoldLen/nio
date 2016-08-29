package org.xtwy.thrift;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.util.AttributeKey;

import java.lang.reflect.Method;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TMemoryBuffer;
import org.xtwy.netty.constants.CommonConstant;
import org.xtwy.thrift.codec.ThriftClientEncode;

import com.hzins.thrift.demo.Content;

public class ThriftClient {
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
				    	 ch.pipeline().addLast(new ThriftClientEncode());
				    	 ch.pipeline().addLast(new DelimiterBasedFrameDecoder(Integer.MAX_VALUE,Delimiters .lineDelimiter()[0]));
				    	 ch.pipeline().addLast(new ThriftClientHandler());
				    
				     }
				 });
				
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	 public static Object startClient(Object req,Class responseClass) throws Exception {
		     ChannelFuture  f= b.connect("localhost", 8999).sync(); 
		     f.channel().writeAndFlush(req);
			 f.channel().closeFuture().sync();
			   ByteBuf obj = (ByteBuf) f.channel().attr(AttributeKey.valueOf(CommonConstant.ATTRIBUTE_KEY)).get();
				TMemoryBuffer respBuffer = new TMemoryBuffer(1024);
				byte[] b = new byte[obj.readableBytes()];
				obj.readBytes(b);
				respBuffer.write(b);
				TProtocol respProt = new TBinaryProtocol(respBuffer);
				
				Method m = responseClass.getMethod("read", TProtocol.class);
				Object response = responseClass.newInstance();
				m.invoke(response, respProt);
			  return response;
		
	 }
	 
	 
	 
	 
}

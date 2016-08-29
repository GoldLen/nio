package org.xtwy.chatroom;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;

//@Component
public class WebsocketServer  implements ApplicationListener<ContextRefreshedEvent>,Ordered{
	
	private int port;

    public WebsocketServer(int port) {
        this.port = port;
    }
    
    public WebsocketServer() {
    }
    
    public void run(int port) throws Exception{
    	EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
			ServerBootstrap b = new ServerBootstrap(); // (2)
			b.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class) 
			.childHandler(new WebSocketServerInitializer())
			.option(ChannelOption.SO_BACKLOG, 128)
			 .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)
			ChannelFuture f = b.bind(port).sync(); // (7)
			f.channel().closeFuture().sync();
		} finally{
			 workerGroup.shutdownGracefully();
			  bossGroup.shutdownGracefully();
		}
    }

    
    public static void main(String[] args) throws Exception {
        int port=8999;
        new WebsocketServer(port).run(port);
    }

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return Ordered.LOWEST_PRECEDENCE;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		 try {
			run(8999);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

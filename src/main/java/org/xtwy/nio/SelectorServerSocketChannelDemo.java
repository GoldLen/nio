package org.xtwy.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class SelectorServerSocketChannelDemo {
	
	public static void startServer() throws Exception{
		
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.bind(new InetSocketAddress(8999));
		Selector selector = Selector.open();
		serverSocketChannel.configureBlocking(false);
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		
		while(true){
			//每一个连接，只会select一次,每次select的时候，能够将selector中中所有
			//我接受的OP_ACCEPT的通道都拿出来，然后遍历这些通道，完成相应的操作
			int select = selector.select();
			//是否有可用的通道已接入
			if(select>0){
				for(SelectionKey key :selector.selectedKeys()){
					if(key.isAcceptable()){
						SocketChannel socketChannel = ((ServerSocketChannel)key.channel()).accept();
						ByteBuffer buf = ByteBuffer.allocate(40);
						int size = socketChannel.read(buf);
						while(size>0){
							buf.flip();//转换为读的模式，其实是在切换index
							Charset charset = Charset.forName("UTF-8");
							System.out.print(charset.newDecoder().decode(buf).toString());
							size = socketChannel.read(buf);
						}
						buf.clear();
						
						ByteBuffer response = ByteBuffer.wrap("您好!我已经收到了您的请求!".getBytes("UTF-8"));
						socketChannel.write(response);
						socketChannel.close();
						selector.selectedKeys().remove(key);//要手动删除
						
					}
					
					
				}
				
				
			}
			
			
		}
		
		
	}

	
	public static void main(String[] args) throws Exception {
		startServer();
	}
}

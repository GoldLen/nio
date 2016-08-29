package org.xtwy.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;


/**
 * 配合ServerSocketChannelDemo完成对ServerSocket的学习
 * @author Administrator
 *
 */
public class SocketChannelDemo {

	public static void startClient() throws Exception {

		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.connect(new InetSocketAddress("localhost", 8999));
		//默认是阻塞的，当read的时候，会阻塞，直到有数据进来
		// socketChannel.configureBlocking(false);

		String request = "hello 夜行侠老师";
		ByteBuffer buf = ByteBuffer.wrap(request.getBytes("UTF-8"));
		socketChannel.write(buf);

		ByteBuffer rbuf = ByteBuffer.allocate(48);
		int size = socketChannel.read(rbuf); 
		//注意这里面可能读取不到，因为是非阻塞的，读不到的时候，
		//size<0 那么下面的循环直接退出，没有输出服务器端返回的结果，
		//因此注释掉 socketChannel.configureBlocking(false);
		while (size > 0) {
			rbuf.flip();
			Charset charset = Charset.forName("UTF-8");
			System.out.println(charset.newDecoder().decode(rbuf));
			rbuf.clear();
			size = socketChannel.read(rbuf);
		}
		buf.clear();
		rbuf.clear();
		socketChannel.close();

		//Thread.sleep(50000);//让客户端多等一会，让服务器端能够在下次循环中读取数据，否则客户端进程结束了，服务器还没来接接受数据

	}

	public static void main(String[] args) throws Exception {
		startClient();
	}
}

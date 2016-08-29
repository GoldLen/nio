package org.xtwy.aio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.util.concurrent.CountDownLatch;

import javax.swing.text.DefaultEditorKit.CutAction;

public class AioServerDemo {
	private static CountDownLatch latch;
	
	public static void startServer()throws Exception{
		final AsynchronousServerSocketChannel asyServerSocketChannel = AsynchronousServerSocketChannel.open();
		asyServerSocketChannel.bind(new InetSocketAddress(8999));
		latch = new CountDownLatch(1);
		
		asyServerSocketChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel,Void>(){
			@Override
			public void completed(AsynchronousSocketChannel ch,
					Void attachment) {
				try {
					asyServerSocketChannel.accept(attachment, this);
					operate(ch);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void failed(Throwable exc, Void attachment) {
				latch.countDown();
			}
			
		});
		
		latch.await();
	}

	protected static void operate(AsynchronousSocketChannel ch) throws Exception {
		ByteBuffer buf = ByteBuffer.allocate(48);
		int size = ch.read(buf).get();
		while(size>0){
			buf.flip();
			Charset charset = Charset.forName("UTF-8");
			System.out.print(charset.newDecoder().decode(buf).toString());
			size = ch.read(buf).get();
		}
//		ch.close();
		
	}

	public static void main(String[] args) throws Exception {
		startServer();
	}
}

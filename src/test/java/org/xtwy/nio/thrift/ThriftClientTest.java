package org.xtwy.nio.thrift;

import java.lang.reflect.Method;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TMemoryBuffer;
import org.junit.Test;
import org.xtwy.oldthrift.ThriftClient;

import com.hzins.thrift.demo.Content;
import com.hzins.thrift.demo.ThriftRequest;

public class ThriftClientTest {
	
	@Test
	public void testThrift() throws Exception{
		TMemoryBuffer buffer = new TMemoryBuffer(1024);
		TBinaryProtocol proto = new TBinaryProtocol(buffer);
		Content content = new Content();
		content.setId(1);
		content.setPhone("111111");
		content.setPrice(12);
		content.write(proto);
		ThriftRequest request = new ThriftRequest();
		request.setCommand("ThriftGetEmailByContent");
		request.setRequestParam(buffer.getArray());
		 buffer.close();
		
         ByteBuf byteBuf = ThriftClient.startClient(request);
         TMemoryBuffer t = new TMemoryBuffer(1024);
         byte[] responseBytes = new byte[byteBuf.readableBytes()];
         byteBuf.readBytes(responseBytes);
		 t.write(responseBytes);
         Content response = new Content();
     	TBinaryProtocol p = new TBinaryProtocol(t);
         response.read(p);
         byteBuf.release();
         System.out.println("phone===="+response.getPhone());
         
	}
	
}

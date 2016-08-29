package org.xtwy.nio.thrift;

import io.netty.buffer.ByteBuf;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TMemoryBuffer;
import org.junit.Test;
import org.xtwy.thrift.ThriftClient;

import com.hzins.thrift.demo.Content;
import com.hzins.thrift.demo.ThriftRequest;

public class ThriftTest {
	
	
	@Test
	public void testThrift() throws Exception{
		
		//组装一个thriftRequest对象，内容需要有Content对象里面的内容
		//我们需要把它转换成byte[]
		
		ThriftRequest request = new ThriftRequest();
		request.setCommand("ThriftGetEmailByContent");
		Content content = new Content();
		content.setId(12);
		content.setPhone("99999");
		TMemoryBuffer buffer = new TMemoryBuffer(1024);
		TProtocol prot = new TBinaryProtocol(buffer);
		content.write(prot);
		request.setRequestParam(buffer.getArray());
		buffer.close();
		Content c = (Content)ThriftClient.startClient(request,Content.class);
		System.out.println(c.getPhone());
		
	}

}

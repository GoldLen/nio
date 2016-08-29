package org.xtwy.oldthriftrpc;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TMemoryBuffer;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.hzins.thrift.demo.Content;
import com.hzins.thrift.demo.FrameworkService;
import com.hzins.thrift.demo.ThriftRequest;
import com.hzins.thrift.demo.ThriftResponse;

public class NonBlockThriftClient {
	
	public static Object startClient(int port,ThriftRequest request,Class resultType) throws Exception{
		TTransport transport = new TFramedTransport(new TSocket("localhost", port));
	    TProtocol protocol = new TBinaryProtocol(transport);
	    FrameworkService.Client client = new FrameworkService.Client(protocol);
	    transport.open();
	    ThriftResponse result = client.execute(request);
	    
	    return buildBinaryToThirft(result.getBody(),resultType);
		
	}
	public static Object buildBinaryToThirft(byte[] requestParam,Class resultType){
		try {
//			buf.flip();
//			byte[] requestParam = new byte[buf.limit()];
//			requestParam = buf.array();
//					buf.clear();
			Method parameterMethod = resultType.getMethod("read", TProtocol.class);
			Object result = resultType.newInstance();
			TMemoryBuffer buffer = new TMemoryBuffer(requestParam.length);
			buffer.write(requestParam);
			TProtocol prot = new TBinaryProtocol(buffer);
			parameterMethod.invoke(result, prot);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		
		ThriftRequest request = new ThriftRequest();
		request.setCommand("ThriftGetEmailByContent");
		Content content = new Content();
		content.setId(12);
		content.setPhone("99999");
		TMemoryBuffer buffer = new TMemoryBuffer(1024);
		TProtocol prot = new TBinaryProtocol(buffer);
		content.write(prot);
		request.setRequestParam(buffer.getArray());
		Content c = (Content) startClient(8080,request,Content.class);
		System.out.println(c.getPhone());
	}
}

package org.xtwy.thriftrpc;

import java.lang.reflect.Method;

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

public class TNonBlockThriftClient {
	
	public static Object startClient(int port,ThriftRequest request,Class resClass) throws Exception{
		TTransport transport = new TFramedTransport(new TSocket("localhost", port));
	    TProtocol protocol = new TBinaryProtocol(transport);
	    FrameworkService.Client client = new FrameworkService.Client(protocol);
	    transport.open();
	    ThriftResponse response =  client.execute(request);
	    byte[] body = response.getBody();
	    return buildBinaryTOThrift(body,resClass);
	    
	}

	private static Object buildBinaryTOThrift(byte[] body, Class resClass) {
		try {
			Object result = resClass.newInstance();
			Method method = resClass.getMethod("read", TProtocol.class);
			TMemoryBuffer buffer = new TMemoryBuffer(1024);
			TProtocol proto = new TBinaryProtocol(buffer);
			buffer.write(body);
			method.invoke(result, proto);
			buffer.close();
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
		buffer.close();
		Content result = (Content) startClient(8080,request,Content.class);
		System.out.println(result.getPhone());
	}
}

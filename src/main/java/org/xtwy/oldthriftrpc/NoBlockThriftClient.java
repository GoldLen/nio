package org.xtwy.oldthriftrpc;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.hzins.thrift.demo.HelloWorldService;

public class NoBlockThriftClient {
	
	public static void startClient(int port) throws Exception{
		TTransport transport =   new TFramedTransport(new TSocket("localhost", port));
	    TProtocol protocol = new TBinaryProtocol(transport);
	    HelloWorldService.Client client = new HelloWorldService.Client(protocol);
	    transport.open();
	    String result = client.sayHello("zhangsan");
	    System.out.println(result);
		
	}

	public static void main(String[] args) throws Exception {
		startClient(8080);
	}
}

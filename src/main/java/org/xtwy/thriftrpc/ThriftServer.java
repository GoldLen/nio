package org.xtwy.thriftrpc;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import com.hzins.thrift.demo.HelloWorldService;
import com.hzins.thrift.demo.HelloWorldService.Processor;


public class ThriftServer    implements ApplicationListener<ContextRefreshedEvent>,Ordered {
	
	
	
	public static void startServer(int port) throws Exception{
		TProcessor processor = new Processor<HelloWorldService.Iface>(new HelloServiceImpl());
		TServerSocket transport = new TServerSocket(port);
		TServer.Args args = new TServer.Args(transport);
		args.processor(processor);
		args.protocolFactory(new TBinaryProtocol.Factory());
		TServer server =  new TSimpleServer(args);
		server.serve();
	}

	public static void main(String[] args) throws Exception {
		startServer(8080);
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return LOWEST_PRECEDENCE;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		try {
			startServer(8080);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

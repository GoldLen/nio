package org.xtwy.thriftrpc;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;

import com.hzins.thrift.demo.HelloWorldService;
import com.hzins.thrift.demo.HelloWorldService.Processor;


public class TThreadThriftServer  {
	
	
	
	public static void startServer(int port) throws Exception{
		TProcessor processor = new Processor<HelloWorldService.Iface>(new HelloServiceImpl());
		TServerSocket transport = new TServerSocket(port);
		TThreadPoolServer.Args args = new TThreadPoolServer.Args(transport);
		args.processor(processor);
		args.protocolFactory(new TBinaryProtocol.Factory());
		TServer server =  new TThreadPoolServer(args);
		server.serve();
	}

	public static void main(String[] args) throws Exception {
		startServer(8080);
	}

}

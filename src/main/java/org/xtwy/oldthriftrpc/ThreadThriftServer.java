package org.xtwy.oldthriftrpc;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.xtwy.thriftrpc.HelloServiceImpl;

import com.hzins.thrift.demo.HelloWorldService;
import com.hzins.thrift.demo.HelloWorldService.Processor;

public class ThreadThriftServer {
	
	public static void startServer(int port) throws Exception{
		TProcessor processor = new Processor<HelloWorldService.Iface>(new HelloServiceImpl());
		TServerSocket transport = new TServerSocket(port);
		
		TThreadPoolServer.Args ttpsArgs = new TThreadPoolServer.Args(
				transport);
		ttpsArgs.processor(processor);
		ttpsArgs.protocolFactory(new TBinaryProtocol.Factory());
		TServer server = new TThreadPoolServer(ttpsArgs);
		server.serve();
	}

	public static void main(String[] args) throws Exception {
		startServer(8080);
	}
}

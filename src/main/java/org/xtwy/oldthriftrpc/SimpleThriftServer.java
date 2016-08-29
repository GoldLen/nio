package org.xtwy.oldthriftrpc;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;

import com.hzins.thrift.demo.HelloWorldService;

public class SimpleThriftServer {
	
	public static void startServer(int port) throws Exception{
		
		TProcessor tProcessor = new HelloWorldService.Processor<HelloWorldService.Iface>(new HelloWordServiceImpl());
		TServerSocket transport = new TServerSocket(port);
		TServer.Args args = new TServer.Args(transport);
		args.processor(tProcessor);
		args.protocolFactory(new TBinaryProtocol.Factory());
		TServer server = new TSimpleServer(args);
		server.serve();
		
		
	}

	public static void main(String[] args) throws Exception {
		startServer(8080);
	}
}

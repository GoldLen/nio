package org.xtwy.oldthriftrpc;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.xtwy.thriftrpc.HelloServiceImpl;

import com.hzins.thrift.demo.HelloWorldService;
import com.hzins.thrift.demo.HelloWorldService.Processor;

public class NoBlockThriftServer {
	
	public static void startServer(int port) throws Exception{
		TProcessor processor = new Processor<HelloWorldService.Iface>(new HelloServiceImpl());
		TNonblockingServerSocket tnbSocketTransport = new TNonblockingServerSocket(
		port);
		TNonblockingServer.Args tnbArgs = new TNonblockingServer.Args(
				tnbSocketTransport);
		tnbArgs.processor(processor);
		tnbArgs.transportFactory(new TFramedTransport.Factory());
		tnbArgs.protocolFactory(new TBinaryProtocol.Factory());
		TServer server = new TNonblockingServer(tnbArgs);
		server.serve();
	}

	public static void main(String[] args) throws Exception {
		startServer(8080);
	}
}

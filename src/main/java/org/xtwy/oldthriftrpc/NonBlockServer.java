package org.xtwy.oldthriftrpc;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import com.hzins.thrift.demo.FrameworkService;
import com.hzins.thrift.demo.FrameworkService.Processor;

//@Component
public class NonBlockServer implements ApplicationListener<ContextRefreshedEvent>,Ordered{
	public static void startServer(int port) throws Exception{
		TProcessor processor = new Processor<FrameworkService.Iface>(new FrameworkServiceImpl());
		TNonblockingServerSocket transport = new TNonblockingServerSocket(port);
		TNonblockingServer.Args args = new TNonblockingServer.Args(transport);
		args.processor(processor);
		args.transportFactory(new TFramedTransport.Factory());
		args.protocolFactory(new TBinaryProtocol.Factory());
		TServer server =  new TNonblockingServer(args);
		server.serve();
	}


	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		try {
			startServer(8080);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getOrder() {
		return LOWEST_PRECEDENCE;
	}
}

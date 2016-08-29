package org.xtwy.thriftrpc;
import org.apache.thrift.TException;

import com.hzins.thrift.demo.HelloWorldService;
public class HelloServiceImpl implements HelloWorldService.Iface{

	@Override
	public String sayHello(String username) throws TException {
		System.out.println(username);
		return "ok";
	}

}

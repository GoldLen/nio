package org.xtwy.oldthriftrpc;

import org.apache.thrift.TException;

import com.hzins.thrift.demo.HelloWorldService;

public class HelloWordServiceImpl  implements HelloWorldService.Iface{

	@Override
	public String sayHello(String username) throws TException {
		System.out.println("hello");
		return "ok";
	}
}

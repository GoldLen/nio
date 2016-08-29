package org.xtwy.oldthriftrpc;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TMemoryBuffer;
import org.xtwy.media.Media;

import com.hzins.thrift.demo.FrameworkService;
import com.hzins.thrift.demo.ThriftRequest;
import com.hzins.thrift.demo.ThriftResponse;
public class FrameworkServiceImpl implements FrameworkService.Iface{

	@Override
	public ThriftResponse execute(ThriftRequest request) throws TException {
		Object response = Media.execute(request);
		try {
			byte[] b = buildThriftToBinary(response);
			ThriftResponse res = new ThriftResponse();
			res.setBody(b);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	public byte[] buildThriftToBinary(Object msg) throws Exception{
		Method method = msg.getClass().getMethod("write", TProtocol.class);
		TMemoryBuffer buffer = new TMemoryBuffer(1024);
		TProtocol prot = new TBinaryProtocol(buffer);
		method.invoke(msg, prot);
		byte[] b = buffer.getArray();
		return b;
	}
}

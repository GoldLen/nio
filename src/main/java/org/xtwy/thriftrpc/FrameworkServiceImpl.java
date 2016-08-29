package org.xtwy.thriftrpc;

import java.lang.reflect.Method;

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
		 Object content = Media.execute(request);
		 ThriftResponse response = new ThriftResponse();
		 response.setBody(buildThriftToBinary(content));
		return response;
	}

	private byte[] buildThriftToBinary(Object content) {
		try {
			Method m = content.getClass().getMethod("write", TProtocol.class);
			TMemoryBuffer buffer = new TMemoryBuffer(1024);
			TProtocol protocol = new TBinaryProtocol(buffer);
			m.invoke(content, protocol);
			byte[] b = buffer.getArray();
			buffer.close();
			return b;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}

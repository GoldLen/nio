package org.xtwy.oldthrift;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TMemoryBuffer;
import org.omg.CORBA.SystemException;

public class ThriftParser {

	public static byte[] buildThriftJsonOfBinary(Object returnValue) {
		try {
			TMemoryBuffer buffer = new TMemoryBuffer(1024);
			TBinaryProtocol proto = new TBinaryProtocol(buffer);
			Method readObjectMethod = returnValue.getClass().getMethod("write",
					org.apache.thrift.protocol.TProtocol.class);
			readObjectMethod.invoke(returnValue, proto);
			byte[] bytes = Arrays.copyOf(buffer.getArray(), buffer.length());
			return bytes;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}

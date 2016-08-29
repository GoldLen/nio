package org.xtwy.nio;

import org.junit.Test;
import org.xtwy.netty.DiscardClient;

public class NettyClientTest {
	
	
	@Test
	public void test01() throws Exception{
		for(int i=0;i<1;i++){
			long start = System.currentTimeMillis();
			Object obj = DiscardClient.startClient(1);
			if(obj==null){
				throw new RuntimeException("返回数据为空");
			}
			System.out.println(obj);
			long end = System.currentTimeMillis();
			System.out.println("第"+i+"循环耗时"+(end-start)+"ms");
			
		}
		
		
	}
}

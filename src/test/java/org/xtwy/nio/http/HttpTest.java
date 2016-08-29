package org.xtwy.nio.http;

import org.junit.Test;
import org.xtwy.http.HttpClient;
import org.xtwy.http.RequestParam;
import org.xtwy.user.Person;
import org.xtwy.util.JsonUtils;

public class HttpTest {
	
	@Test
	public void testHttpNettyClient() throws Exception{
		RequestParam requestParam = new RequestParam();
		requestParam.setCommand("httpGetEmailByUser");
		
		Person person = new Person();
		person.setId("12");
		person.setUserName("zhangsan");
		requestParam.setParameter(JsonUtils.beanToJson(person));
		
		Object response = HttpClient.startClient(requestParam);
		System.out.println(response.toString());
	}

}

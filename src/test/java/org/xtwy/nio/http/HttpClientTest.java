package org.xtwy.nio.http;

import org.junit.Test;
import org.xtwy.http.HttpClient;
import org.xtwy.http.RequestParam;

public class HttpClientTest {
	
	@Test
	public void getEmailByUser() throws Exception{
       RequestParam req = new RequestParam();
       req.setCommand("httpGetEmailByUser");
       req.setParameter("hello");
       HttpClient.startClient(req);
	}
	
}

/**
 * Copyright (c) 2006-2015 Hzins Ltd. All Rights Reserved. 
 *  
 * This code is the confidential and proprietary information of   
 * Hzins. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Hzins,http://www.hzins.com.
 *  
 */
package org.xtwy.nio.http;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.xtwy.http.RequestParam;
import org.xtwy.util.JsonUtils;

/**
 * <p>
 * 
 * 
 * 
 * </p>
 * 
 * @author hz14121005
 * @date 2015-8-4 下午2:04:13
 * @version
 */
public class AbstractTool  {

    private static HttpClient client = new DefaultHttpClient();

    private static HttpPost post = new HttpPost("http://localhost:8081");


    public AbstractTool() {
    }

    public Object post(String command) {
	Object response=null;
	try {
	    RequestParam request = new RequestParam();
	    request.setCommand(command);
	    request.setParameter("CXXJ14110671459017");
	    String data=JsonUtils.beanToJson(request);
	    StringEntity s = new StringEntity(data, "UTF-8");
	    s.setContentType("application/json");
	    post.setEntity(s);
	    HttpResponse res = client.execute(post);
	    if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
		HttpEntity entity = res.getEntity();
		response= EntityUtils.toString(entity, "UTF-8");
	    }
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
	return response;
    }

}

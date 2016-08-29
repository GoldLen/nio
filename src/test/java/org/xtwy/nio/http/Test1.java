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

import junit.framework.TestCase;

import org.xtwy.http.RequestParam;

/**
 * <p>
 * 
 * 通用HTTP测试
 * 
 * </p>
 * 
 * @author hz14121005
 * @date 2015-8-4 下午1:54:42
 * @version
 */
public class Test1 extends TestCase{


   
    public void test() {
	String command="getCarInfoByEnquiryNum";
	RequestParam request = new RequestParam();
	Object response = new AbstractTool().post(command);
	System.out.println(response);
    }


}

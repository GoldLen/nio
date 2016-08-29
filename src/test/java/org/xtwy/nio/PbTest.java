package org.xtwy.nio;

import org.junit.Test;
import org.xtwy.pb.protocol.EmailProbuf.Email;
import org.xtwy.pb.protocol.UserProbuf.User;
import org.xtwy.user.UserService;

public class PbTest {
	
	@Test
	public void testAddUser() throws Exception{
		UserService userService = new UserService();
		User user = userService.save();
		System.out.println(user.getPhone());
	}
	
	@Test
	public void getEmailByUser() throws Exception{
		UserService userService = new UserService();
		Email email = userService.getEmail();
		System.out.println(email.getContent());
	}
	
}

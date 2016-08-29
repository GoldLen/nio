package org.xtwy.user;

import org.springframework.stereotype.Controller;
import org.xtwy.media.Remote;
import org.xtwy.pb.protocol.EmailProbuf.Email;
import org.xtwy.pb.protocol.ResponseMsgProbuf.ResponseMsg;
import org.xtwy.pb.protocol.UserProbuf.User;

import com.hzins.thrift.demo.Content;
import com.hzins.thrift.demo.ThriftResponse;

@Controller
public class UserController {
	
	@Remote("saveUser")
	public Object saveUser(User user){
		//使用mybatis把user数据插入到数据库
		User.Builder newUser =  user.newBuilder().setPhone("123456");
		newUser.setId(12);
		newUser.setUserName("ssss");
		return newUser.build();
	}

	@Remote("getEmailByUser")
	public Object getEmailByUser(User user){
		//使用mybatis把user数据插入到数据库
		Email.Builder email =  Email.newBuilder().setContent("test").setFromUser("zhangsan").setId(12).setSubject("test");
		ResponseMsg response = ResponseMsg.newBuilder().setResponse(email.build().toByteString()).build();
		return response;
	}
	
	@Remote("httpGetEmailByUser")
	public Object getEmail(String email){
		email = email+"hhhh";
		return email;
	}
	
	
	
	@Remote("ThriftGetEmailByContent")
	public Object getEmail(Content content){
		System.out.println(content.getPhone());
	    content.setId(1);
	    content.setPhone("15626519062");
	    content.setIdIsSet(true);
		return content;
	}
}

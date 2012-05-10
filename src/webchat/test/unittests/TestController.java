package unittests;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

import models.*;

import static play.test.Helpers.*;

public class TestController {
	
	@Test
	public void SimpleTest(){
		assertEquals(1,1);
	}

	@Test
	public void TestAddUser() {
	    running(fakeApplication(inMemoryDatabase()), new Runnable() {
	       public void run() {
	    	   List<User> users = User.find.all();
	    	   int oldlength = users.size();
	           User user = new User();
	           user.setEmail("a.b@aon.at");
	           user.setLastname("Huber");
	           user.setPrename("Ernst");
	           user.setOnline(false);
	           user.save();
	           
	           Channel channel = new Channel();
	    	   channel.name = "Channel 1";
	    	   channel.topic = "Webengineering";
	    	   channel.isread = false;
	    	   channel.save();
	    	   
	    	   
	    	   channel.setUsers(user);   
	    	   channel.saveManyToManyAssociations("users");
	    	   
	           users = User.find.all();   
	           User testuser = new User();
	           testuser = User.find.byId(1);
	           assertEquals(oldlength+1, users.size());
	           assertEquals(testuser.getId(), 1);
	           assertEquals(testuser.getEmail(), user.getEmail());
	           assertEquals(testuser.getLastname(), user.getLastname());
	           assertEquals(channel.users.contains(testuser),true);
	       }
	    });
	}

}

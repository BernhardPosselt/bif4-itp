package unittests;


import org.junit.Test;
import static org.junit.Assert.*;
import websocket.message.ListMapper;

public class TestListMapper {
	
	@Test
	public void testlistmapper_users(){
		ListMapper.registerMap("users", new models.User());
		assertEquals(ListMapper.getmapModel("users"), new models.User());
	}

}

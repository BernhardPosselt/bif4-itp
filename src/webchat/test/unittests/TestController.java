package unittests;

import static org.junit.Assert.*;
import org.junit.Test;
import static play.test.Helpers.*; //benoetigt fuer fake Apps
import models.User; //Usermodel fuer unseren Test

public class TestController {
	
	/*@Test
	public void TestAddUser() {
		//MockDB starten
	    running(fakeApplication(inMemoryDatabase()), new Runnable() {
	    	//DB fertig generiert
	    	public void run() {
	    		//Neuer User wird erzeugt
	    		User user = new User();
	    		
	    		//User-Objekt wird befuellt
	    		user.email = "a.b@aon.at";
	    		user.lastname = "Huber";
		        user.firstname = "Ernst";
		        
		        //User-Objekt wird in der DB gespeichert
		        user.save();
	           
		        //Gespeicherten User aus der DB holen
		        User testuser = new User();
		        testuser = User.find.byId(1);
		        
		        //Pruefen ob User richtig eingetragen wurde
		        assertEquals(testuser.id, 1);
		        assertEquals(testuser.email, user.email);
		        assertEquals(testuser.lastname, user.lastname);
	    	}
	   	});
	}*/
}


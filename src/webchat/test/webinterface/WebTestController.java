package webinterface;




import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.HTMLUNIT;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import org.fluentlenium.adapter.FluentTest;
import org.junit.Test;

import play.libs.F.Callback;
import play.test.TestBrowser;

import static org.fluentlenium.core.filter.FilterConstructor.*;

public class WebTestController extends FluentTest {

	/*@Test
	public void runInBrowser() {
		// Start Local Server with Port 3333
	    running(testServer(3333), HTMLUNIT, new Callback<TestBrowser>() {
	        public void invoke(TestBrowser browser) {
	        	
	        	//Zum Login UI navigieren
	        	goTo("http://localhost:3333/login");
        	   
	        	//Input tag mit id = username wird mit 'Max' befuellt
	        	fill("#username").with("Max");
	        	
	        	//Input tag mit id = passwort wird mit 'Test123" befuellt
	        	fill("#password").with("Test123");	
	        	
	        	//Button mit id = login wird geklickt und Request wird gesendet
	         	submit("#login");
	         	
	         	//Abfrage ob Login funktioniert hat
	         	assertThat(title().contains("Webchat"));
	        }
	    });
	}  */



	/*@Test
	public void runInBrowser() {
	    running(testServer(3333), HTMLUNIT, new Callback<TestBrowser>() {
	        public void invoke(TestBrowser browser) {
		         try {
		        	 	// Start Server with Port 33333
		        	   goTo("http://localhost:3333/Testdata"); 
		        	   goTo("http://localhost:3333/login");
		        	   
		        	   //Login with Username Password
			           fill("#username").with("Glembo");
			           fill("#password").with("test");	 
			           submit("#login");
			           
			           //Fill in Text data
			           fill("#input_field").with("Hallo ich bin ein Test!");
			           Thread.sleep(2000);
			           click("#input_send");
			    	   
			           //Fill in Java Code
			           find("#input_options").find("option", withText("Java")).click();
			    	   Thread.sleep(2000);
			    	   fill("#input_field").with("public static void test(){System.out.println('Hallo Welt); }");
			    	   Thread.sleep(2000);
			    	   click("#input_send");
			 
			    	   //Prove the insertions
			    	   assertThat(title().contains("Webchat"));
			    	   assertThat(find(".stream_name").contains("Channel1"));
			    	   assertThat(find(".stream_meta").contains("Webengineering"));
			    	   assertThat(find(".java plain").equals("public static void test(){System.out.println('Hallo Welt); }"));
				} catch (InterruptedException e) {
	
					e.printStackTrace();
				}

	        }
	    });
	}*/
}

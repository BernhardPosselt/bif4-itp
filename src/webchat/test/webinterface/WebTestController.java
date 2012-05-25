package webinterface;




import org.fluentlenium.adapter.FluentTest;
import org.junit.*;


import play.mvc.*;
import play.test.*;
import play.libs.F.*;


import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

public class WebTestController extends FluentTest {

	  
	@Test
	public void runInBrowser() {
	    running(testServer(3333), HTMLUNIT, new Callback<TestBrowser>() {
	        public void invoke(TestBrowser browser) {
		         try {
		        	   goTo("http://localhost:3333/Testdata"); 
		        	   goTo("http://localhost:3333/login");
		        	   Thread.sleep(1000);
			           fill("#username").with("Glembo");
			    	   Thread.sleep(1000);
			           fill("#password").with("test");
			    	   Thread.sleep(1000);
			           submit("#login");
			    	   Thread.sleep(5000);
				} catch (InterruptedException e) {
	
					e.printStackTrace();
				}

	        }
	    });
	}
	
	/*@Test
	    public void title_of_bing_should_contain_search_query_name() {
	        goTo("http://www.google.at/");
	        fill("#gbqfq").with("Real Madrid");
	        submit("#gbqfba");
	        assertThat(title()).contains("Real Madrid");
	    }*/
}

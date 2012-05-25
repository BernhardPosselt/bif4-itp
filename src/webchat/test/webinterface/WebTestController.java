package webinterface;




import org.fluentlenium.adapter.FluentTest;
import org.fluentlenium.core.filter.*;
import org.junit.*;
import org.openqa.selenium.lift.find.*;
import org.openqa.selenium.*;


import play.mvc.*;
import play.test.*;
import play.libs.F.*;
import scala.collection.parallel.ParIterableLike.Find;
import views.html.helper.select;


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
			    	   Thread.sleep(2000);

			    	   fill("#input_field").with("Hallo ich bin ein Test!");
			    	 
			    	   Thread.sleep(2000);
			    	   click("#input_send");
			    	   Thread.sleep(2000);
			    	   assertThat(title().contains("Webchat"));
			    	   assertThat(find(".stream_name").contains("Channel1"));
			    	   assertThat(find(".stream_meta").contains("Webengineering"));
			    	   assertThat(find(".message line0").contains("Hallo ich bin ein Test!"));
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

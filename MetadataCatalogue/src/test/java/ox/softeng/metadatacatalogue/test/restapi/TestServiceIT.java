package ox.softeng.metadatacatalogue.test.restapi;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/***
 *  This is an integration test.  It relies on the API Service running in a container somewhere
 */

public class TestServiceIT {

	public static Client client;
	
	public static String endpoint = "http://localhost:8082/api";  
	
	@BeforeClass
	public static void Before() {
		client = ClientBuilder.newClient();
	}
	
	@AfterClass
	public static void After() {
		client.close();
	}
	
	@Test
	public void canCallTestService() throws Exception {
		WebTarget target = client.target(endpoint);
		WebTarget resource = target.path("/test");
		
		Invocation.Builder invocationBuilder = resource.request(MediaType.TEXT_PLAIN);
		Response response = invocationBuilder.get();
		//System.out.println("response status: " + response.getStatus());
		assertTrue(200 == response.getStatus());
		String testResult = response.readEntity(String.class);
		//System.out.println("Test result: " + testResult);
		assertNotNull(testResult);
		assertTrue("Hello, world!".equals(testResult));
		
	}
	
}

package ox.softeng.metadatacatalogue.test.restapi;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Test;

/***
 *  This is an integration test.  It relies on the API Service running in a container somewhere
 */

public class TestServiceIT extends APITest {

	@Test
	public void canCallTestService() throws Exception 
	{
		Response response = assertResponseStatus("/test", MediaType.TEXT_PLAIN, null, 200);

		String testResult = response.readEntity(String.class);
		assertNotNull(testResult);
		assertTrue("Hello, world!".equals(testResult));
		
	}
	
}

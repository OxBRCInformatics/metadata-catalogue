package ox.softeng.metadatacatalogue.test.restapi.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Test;

import ox.softeng.metadatacatalogue.test.restapi.APITest;

/***
 *  This is an integration test.  It relies on the API Service running in a container somewhere
 */

public class TestServiceIT extends APITest<Object> {

	@Test
	public void canCallTestService() throws Exception 
	{
		Response response = assertResponseStatus("/test", MediaType.TEXT_PLAIN, null, 200);

		String testResult = response.readEntity(String.class);
		assertNotNull(testResult);
		assertTrue("Hello, world!".equals(testResult));
		
	}

	
	
	@Override
	protected Object getInstance() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getServicePath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Class<? extends Object> getClazz() {
		// TODO Auto-generated method stub
		return null;
	}
	
}

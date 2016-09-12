package ox.softeng.metadatacatalogue.test.restapi;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Test;

public class AuthenticationFilterIT extends APITest{
	
	@Test
	public void authenticationFilterNoToken() throws Exception 
	{
		assertResponseStatus("/testSecured", MediaType.TEXT_PLAIN, null, 401);
	}

	@Test
	public void authenticationFilterMadeUpToken() throws Exception 
	{
		assertResponseStatus("/testSecured", MediaType.TEXT_PLAIN, "123456", 401);
	}

	@Test
	public void authenticationFilterManiuplateToken() throws Exception 
	{
		LoginResponse lr = doSuccessfulLogin("admin@metadatacatalogue.com", "password"); 

		assertTrue(lr.user.getEmailAddress().equalsIgnoreCase("admin@metadatacatalogue.com"));

		String sessionId = lr.cookie + "-";
		
		assertResponseStatus("/testSecured", MediaType.TEXT_PLAIN, sessionId, 401);
	}
	
	@Test
	public void authenticationFilterSucceed() throws Exception {
		LoginResponse lr = doSuccessfulLogin("admin@metadatacatalogue.com", "password");
		
		assertTrue(lr.user.getEmailAddress().equals("admin@metadatacatalogue.com"));

		Response response = assertResponseStatus("/testSecured", MediaType.TEXT_PLAIN, lr.cookie, 200);
		
		String testResult = response.readEntity(String.class);
		assertNotNull(testResult);
		assertTrue("Hello, world!".equals(testResult));

	}

	@Test
	public void authenticationFilterSucceedLogout() throws Exception {
		LoginResponse lr = doSuccessfulLogin("admin@metadatacatalogue.com", "password");
		assertTrue(lr.user.getEmailAddress().equals("admin@metadatacatalogue.com"));
		
		Response response = assertResponseStatus("/testSecured", MediaType.TEXT_PLAIN, lr.cookie, 200);
		
		String testResult = response.readEntity(String.class);
		assertNotNull(testResult);
		assertTrue("Hello, world!".equals(testResult));
		
		doLogout(lr.cookie);
		
		assertResponseStatus("/testSecured", MediaType.TEXT_PLAIN, lr.cookie, 401);
		
	}

}

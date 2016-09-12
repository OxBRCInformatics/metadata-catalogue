package ox.softeng.metadatacatalogue.test.restapi;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Test;

/***
 *  This is an integration test.  It relies on the API Service running in a container somewhere
 */

public class AuthenticationServiceIT extends APITest{

	
	@Test
	public void canSucceedAuthenticate() throws Exception 
	{
		doSuccessfulLogin("admin@metadatacatalogue.com", "password");
	}

	
	@Test
	public void canFailAuthenticate() throws Exception {
		Response resp = doLogin("admin@metadatacatalogue.com", "password1");
		assertTrue(resp.getStatus()==401);
	}

	@Test
	public void isValidSession() throws Exception {
		assertFalse(isValidSession(""));
		LoginResponse lr = doSuccessfulLogin("admin@metadatacatalogue.com", "password");
		assertTrue(isValidSession(lr.cookie));
		doLogout(lr.cookie);
		assertFalse(isValidSession(lr.cookie));
	}

	
	private boolean isValidSession(String sessionCookie)
	{
		Response response = assertResponseStatus("/authentication/isValidSession", MediaType.APPLICATION_JSON, sessionCookie, 200);
		return response.readEntity(Boolean.class);

	}
	
}

package ox.softeng.metadatacatalogue.test.restapi;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Test;

/***
 *  This is an integration test.  It relies on the API Service running in a container somewhere
 */

public class AuthenticationServiceIT extends APITest{

	
	@Test
	public void canSucceedAuthenticate() throws Exception {
		Response resp = doLogin("admin@metadatacatalogue.com", "password");
		assertTrue(resp.getStatus()==200);
		// Need to check that we get a token, and the username is the same
	}

	
	@Test
	public void canFailAuthenticate() throws Exception {
		Response resp = doLogin("admin@metadatacatalogue.com", "password1");
		assertTrue(resp.getStatus()==401);
	}

	@Test
	public void isValidSession() throws Exception {
		assertFalse(isValidSession(""));
		Response resp = doLogin("admin@metadatacatalogue.com", "password");
		assertTrue(resp.getStatus()==200);
		String sessionCookie = getSessionCookie(resp);
		assertTrue(isValidSession(sessionCookie));
		doLogout(sessionCookie);
		assertFalse(isValidSession(sessionCookie));
	}

	
	private boolean isValidSession(String sessionCookie)
	{
		WebTarget target = client.target(endpoint);
		WebTarget resource = target.path("/authentication/isValidSession");
		
		Response response = resource.request(MediaType.APPLICATION_JSON).cookie("JSESSIONID", sessionCookie).get();
		assertTrue(response.getStatus()==200);
		return response.readEntity(Boolean.class);

	}
	
}

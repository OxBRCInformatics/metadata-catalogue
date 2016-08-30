package ox.softeng.metadatacatalogue.test.restapi;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


import javax.ws.rs.client.Client;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import ox.softeng.metadatacatalogue.domain.core.User;

public class AuthenticationFilterIT extends APITest{



	public static Client client;

	public static ObjectMapper jacksonObjectMapper = new ObjectMapper();
	
	@Test
	public void authenticationFilterNoToken() throws Exception {
		
		WebTarget resource = target.path("/testSecured");
		Invocation.Builder invocationBuilder = resource.request(MediaType.TEXT_PLAIN);
		Response response = invocationBuilder.get();
		//System.out.println("response status: " + response.getStatus());
		assertTrue(401 == response.getStatus());

	}

	@Test
	public void authenticationFilterMadeUpToken() throws Exception {
		
		WebTarget resource = target.path("/testSecured");
		Invocation.Builder invocationBuilder = resource.request(MediaType.TEXT_PLAIN).cookie("JSESSIONID", "123456");
		Response response = invocationBuilder.get();
		//System.out.println("response status: " + response.getStatus());
		assertTrue(401 == response.getStatus());

	}

	@Test
	public void authenticationFilterManiuplateToken() throws Exception {
		Response response = doLogin("admin@metadatacatalogue.com", "password");
		assertTrue(200 == response.getStatus());
		User tokenObject = response.readEntity(User.class);
		//String token = tokenObject.getToken();
		String emailAddress = tokenObject.getEmailAddress();
		assertTrue(emailAddress.equalsIgnoreCase("admin@metadatacatalogue.com"));
		String sessionId = getSessionCookie(response);
		sessionId += "-";
		//System.out.println("token received: " + token);
		
		WebTarget resource = target.path("/testSecured");
		Invocation.Builder invocationBuilder = resource.request(MediaType.TEXT_PLAIN).cookie("JSESSIONID", sessionId);
		Response response2 = invocationBuilder.get();
		//System.out.println("response status: " + response2.getStatus());
		assertTrue(401 == response2.getStatus());

	}
	
	@Test
	public void authenticationFilterSucceed() throws Exception {
		Response response = doLogin("admin@metadatacatalogue.com", "password");
		assertTrue(200 == response.getStatus());
		ObjectNode on = response.readEntity(ObjectNode.class);
		User u = jacksonObjectMapper.readValue(on.toString(), User.class); 
		assertTrue(u.getEmailAddress().equals("admin@metadatacatalogue.com"));
		String sessionId = getSessionCookie(response);
		//String token = tokenObject.getToken();
		//System.out.println("token received: " + token);
		
		WebTarget resource = target.path("/testSecured");
		Invocation.Builder invocationBuilder = resource.request(MediaType.TEXT_PLAIN).cookie("JSESSIONID", sessionId);
		response = invocationBuilder.get();
		System.out.println("response status: " + response.getStatus());
		assertTrue(200 == response.getStatus());
		String testResult = response.readEntity(String.class);
		assertNotNull(testResult);
		assertTrue("Hello, world!".equals(testResult));

	}

	@Test
	public void authenticationFilterSucceedLogout() throws Exception {
		Response response = doLogin("admin@metadatacatalogue.com", "password");
		assertTrue(200 == response.getStatus());
		ObjectNode on = response.readEntity(ObjectNode.class);
		User u = jacksonObjectMapper.readValue(on.toString(), User.class); 
		assertTrue(u.getEmailAddress().equals("admin@metadatacatalogue.com"));
		String sessionCookie = getSessionCookie(response);
		//String token = tokenObject.getToken();
		//System.out.println("token received: " + token);
		
		WebTarget resource = target.path("/testSecured");
		Invocation.Builder invocationBuilder = resource.request(MediaType.TEXT_PLAIN).cookie("JSESSIONID", sessionCookie);
		response = invocationBuilder.get();
		System.out.println("response status: " + response.getStatus());
		assertTrue(200 == response.getStatus());
		String testResult = response.readEntity(String.class);
		assertNotNull(testResult);
		assertTrue("Hello, world!".equals(testResult));
		
		doLogout(sessionCookie);
		
		WebTarget resource2 = target.path("/testSecured");
		Invocation.Builder invocationBuilder2 = resource2.request(MediaType.TEXT_PLAIN).cookie("JSESSIONID", sessionCookie);
		Response response2 = invocationBuilder2.get();
		//System.err.println("response status: " + response2.getStatus());
		assertTrue(401 == response2.getStatus());

		

	}

}

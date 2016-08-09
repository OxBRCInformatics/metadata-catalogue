package ox.softeng.metadatacatalogue.test.restapi;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


import javax.ws.rs.client.Client;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import org.junit.Test;

import ox.softeng.metadatacatalogue.restapi.AuthenticationToken;



public class AuthenticationFilterIT extends APITest{



	public static Client client;



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
		Invocation.Builder invocationBuilder = resource.request(MediaType.TEXT_PLAIN).header(HttpHeaders.AUTHORIZATION, "Bearer " + "123456");
		Response response = invocationBuilder.get();
		//System.out.println("response status: " + response.getStatus());
		assertTrue(401 == response.getStatus());

	}

	@Test
	public void authenticationFilterManiuplateToken() throws Exception {
		Response response = doLogin("admin@metadatacatalogue.com", "password");
		assertTrue(200 == response.getStatus());
		AuthenticationToken tokenObject = response.readEntity(AuthenticationToken.class);
		String token = tokenObject.getToken();
		//System.out.println("token received: " + token);
		
		WebTarget resource = target.path("/testSecured");
		Invocation.Builder invocationBuilder = resource.request(MediaType.TEXT_PLAIN).header(HttpHeaders.AUTHORIZATION, "Bearer " + token + "1");
		Response response2 = invocationBuilder.get();
		//System.out.println("response status: " + response2.getStatus());
		assertTrue(401 == response2.getStatus());

	}

	
	@Test
	public void authenticationFilterSucceed() throws Exception {
		Response response = doLogin("admin@metadatacatalogue.com", "password");
		assertTrue(200 == response.getStatus());
		AuthenticationToken tokenObject = response.readEntity(AuthenticationToken.class);
		String token = tokenObject.getToken();
		//System.out.println("token received: " + token);
		
		WebTarget resource = target.path("/testSecured");
		Invocation.Builder invocationBuilder = resource.request(MediaType.TEXT_PLAIN).header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
		response = invocationBuilder.get();
		//System.out.println("response status: " + response.getStatus());
		assertTrue(200 == response.getStatus());
		String testResult = response.readEntity(String.class);
		assertNotNull(testResult);
		assertTrue("Hello, world!".equals(testResult));

	}

}

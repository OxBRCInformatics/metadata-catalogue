package ox.softeng.metadatacatalogue.test.restapi;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.flywaydb.test.junit.FlywayTestExecutionListener;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ox.softeng.metadatacatalogue.db.ApiContext;
import ox.softeng.metadatacatalogue.db.ConnectionProvider;
import ox.softeng.metadatacatalogue.domain.core.User;
import ox.softeng.metadatacatalogue.restapi.transport.ResponseDTO;
import ox.softeng.metadatacatalogue.restapi.transport.UserCredentials;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/context/simple_applicationContext.xml" })
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, 
                         FlywayTestExecutionListener.class })
public abstract class APITest {

	public static String endpoint = "http://localhost:8082/api";  

	public static Client client;
	public static WebTarget target;
	
	static ApiContext apiCtx;
	
	static ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
	
	@BeforeClass
	public static void BeforeClass() throws Exception {
		client = ClientBuilder.newClient();
		target = client.target(endpoint);

	}

	@Before
	public void Before() throws Exception {

		ConnectionProvider cp = new ConnectionProvider(null);
		// Get the Bootstrap user
		apiCtx = new ApiContext(cp, cp.newConnection(), "admin@metadatacatalogue.com", "password");
	}

	@AfterClass
	public static void After() {
		client.close();
	}

	
	public static LoginResponse doLogin() throws JsonProcessingException
	{
		return doSuccessfulLogin("admin@metadatacatalogue.com", "password");
	}

	
	public static Response doLogin(String username, String password) throws JsonProcessingException
	{
		UserCredentials uc = new UserCredentials();
		uc.setUsername(username);
		uc.setPassword(password);
		
		String json = objectMapper.writeValueAsString(uc);
		
		WebTarget target = client.target(endpoint);
		WebTarget resource = target.path("/authentication/login");
		
		Response response = resource.request(MediaType.APPLICATION_JSON).post(Entity.entity(json, MediaType.APPLICATION_JSON));
		return response;
	}
	
	protected static class LoginResponse {
		protected User user;
		protected String cookie;
	}
	
	public static LoginResponse doSuccessfulLogin(String username, String password) throws JsonProcessingException
	{
		Response response = doLogin(username, password);
		assertTrue(200 == response.getStatus());
		LoginResponse lr = new LoginResponse();
		lr.user = response.readEntity(User.class);
		lr.cookie = getSessionCookie(response);
		return lr;
	}
	
	public static Response doLogout(String sessionCookie) throws JsonProcessingException
	{
		WebTarget target = client.target(endpoint);
		WebTarget resource = target.path("/authentication/logout");
		
		Response response = resource.request(MediaType.APPLICATION_JSON).cookie("JSESSIONID", sessionCookie).post(Entity.entity(null, MediaType.APPLICATION_JSON));
		return response;
	}

	public static String getSessionCookie(Response response)
	{
		String sessionId = response.getCookies().get("JSESSIONID").getValue();
		System.out.println("Session ID: " + sessionId);
		return sessionId;
	}
	
	public Response assertResponseStatus(String path, String mediaType, String sessionId, int expectedResponseStatus)
	{
		WebTarget resource = target.path(path);
		Invocation.Builder invocationBuilder = resource.request(mediaType).cookie("JSESSIONID", sessionId);
		Response response = invocationBuilder.get();
		//System.out.println("response status: " + response2.getStatus());
		assertTrue(expectedResponseStatus == response.getStatus());
		return response;
	}

	public <T> T assertSuccessfulPost(String path, String sessionId, Object body, Class<T> clazz) throws IOException
	{
		WebTarget resource = target.path(path);
		Invocation.Builder invocationBuilder = resource.request(MediaType.APPLICATION_JSON).cookie("JSESSIONID", sessionId);
		String json;
		try {
			json = objectMapper.writeValueAsString(body);
			Response response = invocationBuilder.post(Entity.entity(json, MediaType.APPLICATION_JSON));
			assertTrue(200 == response.getStatus());
			ResponseDTO respObj = (ResponseDTO) response.readEntity(ResponseDTO.class);

			assertTrue(response.getStatus()==200);
			assertTrue(respObj.isSuccess());
			assertTrue(respObj.getErrorMessages() == null || respObj.getErrorMessages().size() == 0);
			assertTrue(respObj.getReturnObjectType().contains(clazz.getSimpleName()));
			
			System.err.println(respObj.getReturnObject());
			T retObj = objectMapper.treeToValue(respObj.getReturnObject(), clazz);
			
			return retObj;
		} 
		catch (JsonProcessingException e) 
		{
			e.printStackTrace();
			fail();
			return null;
		}
		
	}

	
}

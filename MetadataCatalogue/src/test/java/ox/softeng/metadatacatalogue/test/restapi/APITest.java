package ox.softeng.metadatacatalogue.test.restapi;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
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
	
	static ObjectMapper objectMapper = new ObjectMapper();
	
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

	
	public static Response doLogin() throws JsonProcessingException
	{
		return doLogin("admin@metadatacatalogue.com", "password");
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

	
}

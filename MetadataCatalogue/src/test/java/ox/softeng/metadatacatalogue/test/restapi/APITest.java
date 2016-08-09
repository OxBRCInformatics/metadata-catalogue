package ox.softeng.metadatacatalogue.test.restapi;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ox.softeng.metadatacatalogue.restapi.UserCredentials;


public class APITest {

	public static String endpoint = "http://localhost:8082/api";  

	public static Client client;
	public static WebTarget target;
	
	@BeforeClass
	public static void Before() {
		client = ClientBuilder.newClient();
		target = client.target(endpoint);
	}
	
	@AfterClass
	public static void After() {
		client.close();
	}

	
	public static Response doLogin(String username, String password) throws JsonProcessingException
	{
		UserCredentials uc = new UserCredentials();
		uc.setUsername(username);
		uc.setPassword(password);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(uc);
		
		WebTarget target = client.target(endpoint);
		WebTarget resource = target.path("/authentication");
		
		Response response = resource.request(MediaType.APPLICATION_JSON).post(Entity.entity(json, MediaType.APPLICATION_JSON));
		return response;
	}
	
}

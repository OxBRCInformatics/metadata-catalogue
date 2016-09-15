package ox.softeng.metadatacatalogue.db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.SecurityContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ox.softeng.metadatacatalogue.api.UserApi;
import ox.softeng.metadatacatalogue.domain.core.User;

public class ApiContext extends DatabaseQueryHelper implements SecurityContext {

	private static ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();


	private User user;

	private ApiContext(Properties props)
	{
		cp = new ConnectionProvider(props);
		emf = cp.newConnection();
		user = null;
	}

	public ApiContext(String propertiesFilename) throws Exception
	{
		Properties props = readPropertiesFile(propertiesFilename);
		String username = props.getProperty("apiUser.username");
		String password = props.getProperty("apiUser.password");
		getUser(username, password);
	}

	public ApiContext(String properiesFilename, String username, String password) throws Exception
	{
		Properties props = new Properties();
		props.load(new FileInputStream(properiesFilename));
		cp = new ConnectionProvider(props);
		emf = cp.newConnection();
		getUser(username, password);
	}

	private Properties readPropertiesFile(String propertiesFilename) throws FileNotFoundException, IOException
	{
		Properties props = new Properties();
		props.load(new FileInputStream(propertiesFilename));
		cp = new ConnectionProvider(props);
		emf = cp.newConnection();
		return props;
	}
	
	public ApiContext(ConnectionProvider cp, EntityManagerFactory emf, String username, String password) throws Exception
	{
		this.emf = emf;
		this.cp = cp;
		getUser(username, password);
	}

	private ApiContext(ConnectionProvider cp, EntityManagerFactory emf, User u) throws Exception
	{
		this.emf = emf;
		this.cp = cp;
		this.user = u;
	}


	public static ApiContext getMasterApiContext(Properties props)
	{
		ApiContext apiCtx = new ApiContext(props);
		return apiCtx;
	}
	
	public static ApiContext getSessionContext(ApiContext masterContext, User u) throws Exception
	{
		ApiContext apiCtx = new ApiContext(masterContext.getCp(), masterContext.getEmf(), u);
		return apiCtx;
	}

	private void getUser(String username, String password) throws Exception
	{
		User u = UserApi.getByEmailAddressAndPassword(this, username, password);
		if(u != null)
		{
			this.user = u;
		}
		else
		{
			System.err.println("Cannot get ApiContext: no user found with username '" + username + "' and the provided password.");
		}
	}

	public User getUser() {
		return user;
	}

	public void close()
	{
		if(emf != null && emf.isOpen())
		{
			emf.close();
		}
	}

	@Override
	public String getAuthenticationScheme() {
		return null;
	}

	@Override
	public Principal getUserPrincipal() {
		return user;
	}

	@Override
	public final boolean isSecure() {
		return false;
	}

	@Override
	public boolean isUserInRole(String role) {
		User.UserRole userRole = User.UserRole.valueOf(role);

		for(User.UserRole u : User.UserRole.values())
		{
			if(u == user.getUserRole())
			{
				return false;
			}
			else if(u == userRole)
			{
				return true;
			}
		}
		return false;
	}
	

	public <T> T createObjectFromInputStream(InputStream is, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException
	{
  		return objectMapper.readValue(is, clazz);
		
	}
	

}

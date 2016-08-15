package ox.softeng.metadatacatalogue.restapi.services;

import java.security.Key;

import javax.ws.rs.core.Context;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.swagger.annotations.Api;
import ox.softeng.metadatacatalogue.api.UserApi;
import ox.softeng.metadatacatalogue.db.ApiContext;
import ox.softeng.metadatacatalogue.domain.core.User;
import ox.softeng.metadatacatalogue.restapi.transport.UserCredentials;
import ox.softeng.metadatacatalogue.restapi.transport.UserDetails;


@Api(value = "Authentication")
@Path("/authentication")

public class AuthenticationService extends BasicCatalogueService{

	@Path("/login")
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public UserDetails login(@Context HttpServletRequest request, UserCredentials credentials) 
	{
		if(credentials == null)
		{
			throw new javax.ws.rs.BadRequestException("Username and Password not provided",Response.status(Response.Status.BAD_REQUEST).build());
		}
		String username = credentials.getUsername().trim();
		String password = credentials.getPassword().trim();

		ApiContext apiContext = (ApiContext) request.getServletContext().getAttribute("masterApiContext");
		if(apiContext==null){
			throw new javax.ws.rs.ServerErrorException("Entity Manager error",Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
		}


		Key key = (Key) request.getServletContext().getAttribute("tokenKey");
		if(key==null)
		{
			throw new javax.ws.rs.ServerErrorException("Null token error",Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
		}
		//System.out.println("Authentication here...");
		
		// Authenticate the user using the credentials provided
		ApiContext sessionContext;
		try {
			sessionContext = authenticateUser(apiContext, username, password);
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new javax.ws.rs.NotAuthorizedException("Exception thrown during authentication", Response.status(Response.Status.UNAUTHORIZED).build());
		} 
		if(sessionContext != null)
		{
			request.getSession().setAttribute("apiContext", sessionContext);
			// Return the token on the response
			
			UserDetails destObject = mapper.map(sessionContext.getUser(), UserDetails.class);
			        
			
			return destObject;
		}
		else 
		{
			throw new javax.ws.rs.NotAuthorizedException("Invalid username or password", Response.status(Response.Status.UNAUTHORIZED).build());
		}
	}
	
	
	@Path("/logout")
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public boolean logout(@Context HttpServletRequest request)
	{
		request.getSession().setAttribute("apiContext", null);
		request.getSession().invalidate();
		return true;
	}


	private ApiContext authenticateUser(ApiContext apiCtx, String username, String password) throws Exception
	{

		User u = UserApi.getByEmailAddressAndPassword(apiCtx, username, password);
		if(u == null || !u.getEmailAddress().equalsIgnoreCase(username) || u.getId() == null)
		{
			return null;
		}
		ApiContext mcsc = ApiContext.getSessionContext(apiCtx, u);

		return mcsc;


	}

}
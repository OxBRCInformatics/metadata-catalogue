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

import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.lang.JoseException;

import ox.softeng.metadatacatalogue.api.ApiContext;
import ox.softeng.metadatacatalogue.api.UserApi;
import ox.softeng.metadatacatalogue.domain.core.User;
import ox.softeng.metadatacatalogue.restapi.AuthenticationToken;
import ox.softeng.metadatacatalogue.restapi.CatalogueSecurityContext;
import ox.softeng.metadatacatalogue.restapi.UserCredentials;



@Path("/authentication")
public class AuthenticationService{

	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public AuthenticationToken authenticate(@Context HttpServletRequest request, UserCredentials credentials) 
	{
		if(credentials == null)
		{
			throw new javax.ws.rs.BadRequestException("Username and Password not provided",Response.status(Response.Status.BAD_REQUEST).build());
		}
		String username = credentials.getUsername();
		String password = credentials.getPassword();

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
		CatalogueSecurityContext mcsc;
		try {
			mcsc = authenticateUser(apiContext, username, password);
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new javax.ws.rs.NotAuthorizedException("Exception thrown during authentication", Response.status(Response.Status.UNAUTHORIZED).build());
		} 
		if(mcsc != null)
		{
			// Issue a token for the user
			AuthenticationToken token;
			try {
				
				token = issueToken(mcsc, key);
				token.setUsername(((CatalogueSecurityContext.CatalogueSecurityPrincipal)mcsc.getUserPrincipal()).getUsername());
				token.setFirstName(((CatalogueSecurityContext.CatalogueSecurityPrincipal)mcsc.getUserPrincipal()).getFirstName());
				token.setLastName(((CatalogueSecurityContext.CatalogueSecurityPrincipal)mcsc.getUserPrincipal()).getLastName());
				token.setRole(((CatalogueSecurityContext.CatalogueSecurityPrincipal)mcsc.getUserPrincipal()).getUserRole());
			} catch (JoseException e) {
				e.printStackTrace();
				throw new javax.ws.rs.ServerErrorException("Error generating authentication token",Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
			}

			// Return the token on the response
			return token;
		}
		else 
		{
			throw new javax.ws.rs.NotAuthorizedException("Invalid username or password", Response.status(Response.Status.UNAUTHORIZED).build());
		}
	}

	private CatalogueSecurityContext authenticateUser(ApiContext apictx, String username, String password) throws Exception
	{

		User u = UserApi.getByEmailAddressAndPassword(apictx, username, password);
		if(u == null || !u.getEmailAddress().equalsIgnoreCase(username) || u.getId() == null)
		{
			return null;
		}
		CatalogueSecurityContext mcsc = new CatalogueSecurityContext(u);

		return mcsc;


	}

	private AuthenticationToken issueToken(CatalogueSecurityContext securityContext, Key key) throws JoseException {
		// It's up to you (use a random String persisted to the database or a JWT token)
		// The issued token must be associated to a user
		JsonWebEncryption jwe = new JsonWebEncryption();
		jwe.setPayload(securityContext.toJSON().toString());
		jwe.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.A128KW);
		jwe.setEncryptionMethodHeaderParameter(ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256);
		jwe.setKey(key);
		String serializedJwe = jwe.getCompactSerialization();

		AuthenticationToken t = new AuthenticationToken();
		t.setToken(serializedJwe);
		return t;
	}
}
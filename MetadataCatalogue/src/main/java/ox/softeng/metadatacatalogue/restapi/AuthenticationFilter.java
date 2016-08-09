package ox.softeng.metadatacatalogue.restapi;

import java.io.IOException;
import java.lang.reflect.Method;
import java.security.Key;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ext.Provider;

import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.lang.JoseException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

	@Context
    HttpServletRequest webRequest;
	
	@Context
	ResourceInfo resourceInfo;
	  
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {

		// Get the HTTP Authorization header from the request
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

		//System.out.println("Authentication Filter running...");
		// Check if the HTTP Authorization header is present and formatted correctly
		
		if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer "))
		{
			if (!allowUnAuthenticated())
			{
				throw new NotAuthorizedException("Authorization header must be provided");
			}
			else
			{
				CatalogueSecurityContext msc = new CatalogueSecurityContext();
				requestContext.setSecurityContext(msc);
			}
			
		}
		else
		{
		
		
			// Extract the token from the HTTP Authorization header
			String token = authorizationHeader.substring("Bearer".length()).trim();
	
			try {
	        	Key key = (Key) webRequest.getServletContext().getAttribute("tokenKey");
	        	if(key==null)
	        	{
	        		throw new javax.ws.rs.InternalServerErrorException("Cannot retrieve token key from global session", Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
	        	}
				JsonWebEncryption jwe = new JsonWebEncryption();
				jwe.setKey(key);
				try{
					jwe.setCompactSerialization(token);
					String payload = jwe.getPayload();
					
					ObjectMapper mapper = new ObjectMapper();
					JsonNode jsonNode = mapper.readTree(payload);
					
					requestContext.setSecurityContext(CatalogueSecurityContext.fromJSON(jsonNode));
					
				}catch(JoseException je)
				{
					//je.printStackTrace(System.err);
					requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
				}
	
			} 
			catch (Exception e) {
				e.printStackTrace();
				requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
				
			}
		}
	}
	
	boolean allowUnAuthenticated()
	{
	    Method resourceMethod = resourceInfo.getResourceMethod();
	    Secured secAnnot = resourceMethod.getAnnotation(Secured.class);
	    if (secAnnot != null) {
	    	return secAnnot.allowUnAuthenticated();
	    }
	    else
	    {
	    	System.err.println("Cannot determine whether this method allows those who haven't authenticated!");
	    	return false;
	    }
	}
}
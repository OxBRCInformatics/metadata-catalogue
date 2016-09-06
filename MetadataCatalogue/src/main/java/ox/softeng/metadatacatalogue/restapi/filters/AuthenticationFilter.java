package ox.softeng.metadatacatalogue.restapi.filters;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ext.Provider;

import ox.softeng.metadatacatalogue.db.ApiContext;
import ox.softeng.metadatacatalogue.restapi.Secured;

import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
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

		ApiContext apiContext = (ApiContext) webRequest.getSession().getAttribute("apiContext");
		if(apiContext == null)
		{
			if (!allowUnAuthenticated())
			{
				requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("This user is not able to access this resource").build());
			}
			else
			{
				requestContext.setSecurityContext((ApiContext)webRequest.getServletContext().getAttribute("masterApiContext"));
			}
		}
		else
		{
			requestContext.setSecurityContext(apiContext);
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
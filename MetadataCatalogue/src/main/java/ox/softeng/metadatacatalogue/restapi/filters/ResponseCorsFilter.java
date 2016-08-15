package ox.softeng.metadatacatalogue.restapi.filters;


import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.HEADER_DECORATOR)
public class ResponseCorsFilter implements ContainerResponseFilter {
 
  
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
 
		
		responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
		responseContext.getHeaders().add("Access-Control-Allow-Headers","origin, content-type, accept, authorization");
		responseContext.getHeaders().add("Access-Control-Allow-Methods","GET, POST, PUT, DELETE, OPTIONS, HEAD");
        		
	}
	
 
}
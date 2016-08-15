package ox.softeng.metadatacatalogue.restapi.filters;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.Priority;
import javax.servlet.ServletContext;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.HEADER_DECORATOR)
public class ResponseCorsFilter implements ContainerResponseFilter {
 
	static String allowOrigin = null;
	
	public ResponseCorsFilter(@Context ServletContext context) throws IOException
	{
		getAllowOrigin(context);
	}
	
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		
		responseContext.getHeaders().add("Access-Control-Allow-Origin", allowOrigin);
		responseContext.getHeaders().add("Access-Control-Allow-Headers","origin, content-type, accept, authorization");
		responseContext.getHeaders().add("Access-Control-Allow-Methods","GET, POST, PUT, DELETE, OPTIONS, HEAD");
		responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
        		
	}
	
	private void getAllowOrigin(ServletContext context) throws IOException
	{
		Properties props = new Properties();
		InputStream inputStream = context.getResourceAsStream("/META-INF/webapi.properties");
		props.load(inputStream);
		String host = props.getProperty("webapi.host");
		String port = props.getProperty("webapi.port");
		allowOrigin = "http://" + host + ":" + port;
		System.out.println("setting allowOrigin: " + allowOrigin);
		
	}
 
}
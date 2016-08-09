package ox.softeng.metadatacatalogue.restapi;
import java.io.IOException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import ox.softeng.metadatacatalogue.api.ApiContext;

public class DBConnectionFilter implements ContainerRequestFilter {

	@Context
    HttpServletRequest webRequest;
    
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException  
	{ 
		ServletContext application = webRequest.getServletContext();  

		ApiContext apiCtx = (ApiContext) application.getAttribute("masterApiContext");
		EntityManagerFactory emf = apiCtx.getEmf();
		
		if(emf==null || !emf.isOpen())
		{
			System.err.println("Entity Manager Factory is not set - re-initialising...");
			try{
				Properties props = (Properties) application.getAttribute("entityManagerProps");
				((ApplicationContext)application).initialiseDatabaseConnection( webRequest.getServletContext(), props);
			}
			catch(Exception e){
				e.printStackTrace(System.err);
				throw new javax.ws.rs.NotAuthorizedException("Unable to connect to system database", Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
			}
		}
		else{
			//System.err.println("DBServer retrieved from application settings");
		}

	} 


}

package ox.softeng.metadatacatalogue.restapi;

import java.util.Properties;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;


public class BasicCatalogueService {

	
	@Context HttpServletRequest request;
	@Context SecurityContext securityContext;

	
	
	
	protected UUID getUserId()
	{
		if(securityContext.getUserPrincipal() != null)
		{
			return ((CatalogueSecurityContext.CatalogueSecurityPrincipal) securityContext.getUserPrincipal()).getUserId();
		}
		else return null;
	}

	protected EntityManager getNewEntityManager()
	{
		EntityManagerFactory entityManagerFactory = (EntityManagerFactory) request.getServletContext().getAttribute("entityManagerFactory");
		if(entityManagerFactory==null){
			System.err.println("No entity manager factory!");
			throw new javax.ws.rs.ServerErrorException("Error on getting Entity Manager Factory",Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
		}
		Properties props = (Properties) request.getServletContext().getAttribute("entityManagerProps");
		if(props==null){
			System.err.println("No entity manager properties!");
			throw new javax.ws.rs.ServerErrorException("Error on getting Entity Manager Properties",Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
		}
		return entityManagerFactory.createEntityManager(props);
	}

}

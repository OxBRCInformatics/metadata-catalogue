package ox.softeng.metadatacatalogue.restapi.services;

import java.util.Properties;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import ox.softeng.metadatacatalogue.db.ApiContext;
import ox.softeng.metadatacatalogue.domain.core.User;

public class BasicCatalogueService {

	
	@Context HttpServletRequest request;
	@Context SecurityContext securityContext;

	
	static Mapper mapper = new DozerBeanMapper();

	
	protected UUID getUserId()
	{
		if(securityContext.getUserPrincipal() != null)
		{
			return ((User) securityContext.getUserPrincipal()).getId();
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
	
	protected ApiContext getApiContext()
	{
		
		return (ApiContext) request.getSession().getAttribute("apiContext");
	}

}

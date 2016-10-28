package ox.softeng.metadatacatalogue.restapi.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.node.ArrayNode;

import ox.softeng.metadatacatalogue.api.UserApi;
import ox.softeng.metadatacatalogue.domain.core.User;
import ox.softeng.metadatacatalogue.restapi.Secured;
import ox.softeng.metadatacatalogue.restapi.transport.ResponseDTO;

@Path("/user")
public class UserService extends BasicCatalogueService {
	
	//public static Class<? extends CatalogueItem> type = Metadata.class;
	
	@Path("/create")
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Secured(allowUnAuthenticated=false)
	public ResponseDTO createUser(User newUser) throws Exception
	{	
		System.err.println(newUser.getFirstName());
		System.err.println(newUser.getLastName());
		System.err.println(newUser.getEmailAddress());
		System.err.println(new String(newUser.getPassword()));
		User u = UserApi.createUser(getApiContext(), newUser.getFirstName(), newUser.getLastName(), newUser.getEmailAddress(), new String(newUser.getPassword()), User.UserRole.EDITOR);
		return createSuccessfulResponse(u, "user.id");
	}
		
	
	@Path("/all")
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Secured(allowUnAuthenticated=false)
	public ArrayNode getAllUsers() throws Exception
	{
		ArrayNode us = getApiContext().getAllMap(User.class, "user.id");
		return us;
	}
}
		

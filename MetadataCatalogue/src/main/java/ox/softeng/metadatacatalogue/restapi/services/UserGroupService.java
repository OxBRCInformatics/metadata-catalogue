package ox.softeng.metadatacatalogue.restapi.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.node.ArrayNode;

import ox.softeng.metadatacatalogue.api.UserGroupApi;
import ox.softeng.metadatacatalogue.domain.core.UserGroup;
import ox.softeng.metadatacatalogue.restapi.Secured;
import ox.softeng.metadatacatalogue.restapi.transport.ResponseDTO;

@Path("/usergroup")
public class UserGroupService extends BasicCatalogueService {
	
	//public static Class<? extends CatalogueItem> type = Metadata.class;
	
	@Path("/create")
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Secured(allowUnAuthenticated=false)
	public ResponseDTO createUser(UserGroup newUserGroup) throws Exception
	{	
		UserGroup ug = UserGroupApi.createUserGroup(getApiContext(), newUserGroup.getGroupName());
		return createSuccessfulResponse(ug, "usergroup.id");
	}
	
	@Path("/all")
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Secured(allowUnAuthenticated=false)
	public ArrayNode getAllUserGroups() throws Exception
	{
		ArrayNode ugs = getApiContext().getAllMap(UserGroup.class, "user.id");
		return ugs;
	}

		
}

package ox.softeng.metadatacatalogue.restapi.services;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ox.softeng.metadatacatalogue.restapi.Secured;

@Path("/")
public class TestService  extends BasicCatalogueService{
	
	
	@Path("/test")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	//@ApiOperation(value="Test service", 
	//			  notes="This is simply designed to test the operation of the underlying API.  It could be used as a ping service, or something")
	@Secured(allowUnAuthenticated= true)
	public Response test() {
 
		String output = "Hello, world!";
 
		return Response.status(200).entity(output).build();
 
	}
	
	@Path("/testSecured")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Secured(allowUnAuthenticated= false)
	public Response testSecured() {
 
		String output = "Hello, world!";
		return Response.status(200).entity(output).build();
 
	}

	@Path("/testRoleSecured")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Secured(allowUnAuthenticated= false)
	@RolesAllowed({"User"})
	public Response testRoleSecured() {
 
		String output = "Hello, world!";
 		return Response.status(200).entity(output).build();
 
	}

}

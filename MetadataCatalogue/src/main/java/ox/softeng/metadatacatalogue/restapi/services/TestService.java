package ox.softeng.metadatacatalogue.restapi.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import ox.softeng.metadatacatalogue.restapi.BasicCatalogueService;
import ox.softeng.metadatacatalogue.restapi.Secured;

//@Api(value="Test Service")
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
}

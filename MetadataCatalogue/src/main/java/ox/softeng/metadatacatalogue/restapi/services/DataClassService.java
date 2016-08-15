package ox.softeng.metadatacatalogue.restapi.services;

import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.swagger.annotations.Api;
import ox.softeng.metadatacatalogue.domain.core.DataClass;
import ox.softeng.metadatacatalogue.restapi.Secured;
import ox.softeng.metadatacatalogue.restapi.transport.pageview.DataClassDTO;


@Api(value = "Data Class")
@Path("/dataclass")
public class DataClassService extends BasicCatalogueService{


	@Path("/pageView/{id}")
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Secured(allowUnAuthenticated= true)
	public DataClassDTO getDataClass(@PathParam("id") UUID dataClassId) throws Exception
	{
		return getApiContext().getByIdMap(DataClass.class, DataClassDTO.class, dataClassId);
		
	}


}
package ox.softeng.metadatacatalogue.restapi.services;

import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import ox.softeng.metadatacatalogue.domain.core.DataClass;
import ox.softeng.metadatacatalogue.restapi.Secured;
import ox.softeng.metadatacatalogue.restapi.transport.SearchParamsDTO;


@Path("/dataclass")
public class DataClassService extends DataModelComponentService{


	@Path("/pageView/{id}")
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Secured(allowUnAuthenticated= true)
	public JsonNode getDataClass(@PathParam("id") UUID dataClassId) throws Exception
	{
		
		return getApiContext().getByIdMap(DataClass.class, "dataclass.pageview.id", dataClassId);
		//return getApiContext().getByIdMap(DataClass.class, DataClassDTO.class, dataClassId);
		
	}

	@Path("/search")
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Secured(allowUnAuthenticated= true)
	public ArrayNode searchDataClass(SearchParamsDTO searchParams) throws Exception
	{
		
		return getApiContext().searchMap(
				DataClass.class, "dataclass.pageview.id", 
				searchParams.getSearchTerm(), searchParams.getOffset(), searchParams.getLimit());
	}



}

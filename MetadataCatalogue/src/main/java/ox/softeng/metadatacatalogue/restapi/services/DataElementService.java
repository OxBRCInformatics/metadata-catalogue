package ox.softeng.metadatacatalogue.restapi.services;

import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.node.ArrayNode;

import io.swagger.annotations.Api;
import ox.softeng.metadatacatalogue.domain.core.DataElement;
import ox.softeng.metadatacatalogue.restapi.Secured;
import ox.softeng.metadatacatalogue.restapi.transport.SearchParamsDTO;

@Api(value = "Data Element")
@Path("/dataelement")
public class DataElementService extends DataModelComponentService{


	@Path("/pageView/{id}")
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Secured(allowUnAuthenticated= true)
	public ArrayNode getDataElement(@PathParam("id") UUID dataElementId) throws Exception
	{
		//return getApiContext().getByIdMap(DataElement.class, DataElementDTO.class, dataElementId);
		return null;
	}

	@Path("/search")
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Secured(allowUnAuthenticated= true)
	public ArrayNode searchDataElement(SearchParamsDTO searchParams) throws Exception
	{
		
		return getApiContext().searchMap(
				DataElement.class, "dataelement.pageview.id", 
				searchParams.getSearchTerm(), searchParams.getOffset(), searchParams.getLimit());
	}



}

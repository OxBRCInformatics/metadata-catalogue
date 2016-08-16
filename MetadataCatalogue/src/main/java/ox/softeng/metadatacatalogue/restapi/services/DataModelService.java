package ox.softeng.metadatacatalogue.restapi.services;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.swagger.annotations.Api;
import ox.softeng.metadatacatalogue.domain.core.DataModel;
import ox.softeng.metadatacatalogue.restapi.Secured;
import ox.softeng.metadatacatalogue.restapi.transport.SearchParamsDTO;
import ox.softeng.metadatacatalogue.restapi.transport.pageview.DataModelDTO;
import ox.softeng.metadatacatalogue.restapi.transport.treeview.DataModelTreeDTO;


@Api(value = "Data Model")
@Path("/datamodel")
public class DataModelService extends BasicCatalogueService{


	@Path("/pageView/{id}")
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Secured(allowUnAuthenticated= true)
	public DataModelDTO getDataModel(@PathParam("id") UUID dataModelId) throws Exception
	{
		return getApiContext().getByIdMap(DataModel.class, DataModelDTO.class, dataModelId);
		
	}

	@Path("/all")
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Secured(allowUnAuthenticated= true)
	public List<DataModelDTO> getAllDataModels() throws Exception
	{
		List<DataModelDTO> dms = getApiContext().getAllMap(DataModel.class, DataModelDTO.class);
		return dms;
	}
	
	

	@Path("/tree")
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Secured(allowUnAuthenticated= true)
	public List<DataModelTreeDTO> getDataModelTrees() throws Exception
	{
		return getApiContext().getAllMap(DataModel.class, DataModelTreeDTO.class);
	}

	@Path("/search")
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Secured(allowUnAuthenticated= true)
	public List<DataModelDTO> searchDataModel(SearchParamsDTO searchParams) throws Exception
	{
		
		return getApiContext().searchMap(
				DataModel.class, DataModelDTO.class, 
				searchParams.getSearchTerm(), searchParams.getOffset(), searchParams.getLimit());
	}



}

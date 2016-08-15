package ox.softeng.metadatacatalogue.restapi.services;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


import ox.softeng.metadatacatalogue.api.DataModelApi;
import ox.softeng.metadatacatalogue.domain.core.DataModel;
import ox.softeng.metadatacatalogue.restapi.Secured;
import ox.softeng.metadatacatalogue.restapi.transport.DataModelDTO;
import ox.softeng.metadatacatalogue.restapi.transport.DataModelTreeDTO;



@Path("/datamodel")
public class DataModelService extends BasicCatalogueService{


	@Path("/{id}")
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Secured(allowUnAuthenticated= true)
	public DataModelDTO getDataModel(@PathParam("id") UUID dataModelId) throws Exception
	{
		DataModel dm = DataModelApi.getById(getApiContext(), dataModelId);
		DataModelDTO dataModelDTO = mapper.map(dm, DataModelDTO.class);
		return dataModelDTO;
		
	}

	@Path("/all")
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Secured(allowUnAuthenticated= true)
	public List<DataModelDTO> getAllDataModels() throws Exception
	{
		List<DataModelDTO> dms = getApiContext().getAll(DataModelDTO.class, DataModel.class);
		return dms;
	}
	
	

	@Path("/tree")
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Secured(allowUnAuthenticated= true)
	public List<DataModelTreeDTO> getDataModelTrees() throws Exception
	{
		
		return getApiContext().getAll(DataModelTreeDTO.class, DataModel.class);
	}



}

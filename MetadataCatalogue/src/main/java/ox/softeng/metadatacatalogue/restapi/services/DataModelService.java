package ox.softeng.metadatacatalogue.restapi.services;

import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import ox.softeng.metadatacatalogue.api.DataModelApi;
import ox.softeng.metadatacatalogue.domain.core.DataModel;
import ox.softeng.metadatacatalogue.restapi.Secured;
import ox.softeng.metadatacatalogue.restapi.transport.DataModelDTO;



@Path("/datamodel")
public class DataModelService extends BasicCatalogueService{


	@Path("/{id}")
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Secured(allowUnAuthenticated= true)
	public DataModelDTO getDataModel(@PathParam("id") UUID dataModelId) throws Exception{
		
		
		DataModel dm = DataModelApi.getById(getApiContext(), dataModelId);
		Mapper mapper = new DozerBeanMapper();
		DataModelDTO dataModelDTO = mapper.map(dm, DataModelDTO.class);
		return dataModelDTO;
		
	}
	/*
	@Path("/{tree}")
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Secured(allowUnAuthenticated= true)
	public 
*/


}

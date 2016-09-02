package ox.softeng.metadatacatalogue.restapi.services;

import ox.softeng.metadatacatalogue.domain.core.DataSet;
import ox.softeng.metadatacatalogue.restapi.Secured;
import ox.softeng.metadatacatalogue.restapi.transport.ResponseDTO;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ox.softeng.metadatacatalogue.api.DataSetApi;
import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;

@Path("/dataset")
public class DataSetService extends DataModelService {
	
	public static Class<? extends CatalogueItem> type = DataSet.class;
	
	@Path("/create")
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Secured(allowUnAuthenticated=false)
	public ResponseDTO createDataSet(DataSet ds) throws Exception
	{		
		DataSet ret = DataSetApi.createDataSet( getApiContext(), ds.getLabel(), ds.getDescription(), ds.getAuthor(), ds.getOrganization());
		return createSuccessfulResponse(ret, "datamodel.pageview.id");
	}
		
}

package ox.softeng.metadatacatalogue.restapi.services;


import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ox.softeng.metadatacatalogue.api.DataModelComponentApi;
import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;
import ox.softeng.metadatacatalogue.domain.core.Classifier;
import ox.softeng.metadatacatalogue.domain.core.DataModelComponent;
import ox.softeng.metadatacatalogue.restapi.Secured;
import ox.softeng.metadatacatalogue.restapi.transport.ResponseDTO;


public abstract class DataModelComponentService extends SharableService {
	
	public static Class<? extends CatalogueItem> type = DataModelComponent.class;
	

	@Path("/addClassifier/{id}")
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Secured(allowUnAuthenticated=false)
	public ResponseDTO addClassifier(@PathParam("id") UUID dmcId, Classifier cl) throws Exception
	{
		DataModelComponent dmc = getApiContext().getById(DataModelComponent.class, dmcId);

		Classifier newCl = getApiContext().getById(Classifier.class, cl.getId());
		
		Classifier ret = DataModelComponentApi.addClassifier(getApiContext(), dmc, newCl);

		return this.createSuccessfulResponse(ret, "classifier.pageView.id");
		
	}

		
}

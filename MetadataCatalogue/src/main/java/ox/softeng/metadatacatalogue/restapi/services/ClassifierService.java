package ox.softeng.metadatacatalogue.restapi.services;


import ox.softeng.metadatacatalogue.domain.core.Classifier;
import ox.softeng.metadatacatalogue.restapi.Secured;
import ox.softeng.metadatacatalogue.restapi.transport.ResponseDTO;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ox.softeng.metadatacatalogue.api.ClassifierApi;
import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;

@Path("/classifier")
public class ClassifierService extends SharableService {
	
	public static Class<? extends CatalogueItem> type = Classifier.class;
	

	@Path("/create")
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Secured(allowUnAuthenticated=false)
	public ResponseDTO createClassifier(Classifier cl) throws Exception
	{		
		Classifier ret = ClassifierApi.createClassifier( getApiContext(), cl.getLabel(), cl.getDescription());
		ret = (Classifier) maybeAddMetadata(ret, cl);
		return createSuccessfulResponse(ret, "classifier.pageview.id");
	}

		
}

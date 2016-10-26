package ox.softeng.metadatacatalogue.restapi.services;


import ox.softeng.metadatacatalogue.domain.core.Classifier;
import ox.softeng.metadatacatalogue.restapi.Secured;
import ox.softeng.metadatacatalogue.restapi.transport.ResponseDTO;

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

	@Path("/all")
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Secured(allowUnAuthenticated= true)
	public ArrayNode getAllClassifiers() throws Exception
	{
		ArrayNode dms = getApiContext().getAllMap(Classifier.class, "classifier.all");
		return dms;
	}

	@Path("/tree")
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Secured(allowUnAuthenticated= true)
	public ArrayNode getClassifiersTree() throws Exception
	{
		getApiContext().getAll(Classifier.class);
		ArrayNode dms = getApiContext().getAllMap(Classifier.class, "classifier.all");
		return dms;
	}

	@Path("/pageView/{id}")
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Secured(allowUnAuthenticated= true)
	public JsonNode getDataModel(@PathParam("id") UUID classifierId) throws Exception
	{
		return getApiContext().getByIdMap(Classifier.class, "classifier.all", classifierId);
		
	}
	
	@Path("/createChild/{id}")
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Secured(allowUnAuthenticated=false)
	public ResponseDTO createChildClassifier(@PathParam("id") UUID classifierId, Classifier cl) throws Exception
	{		
		Classifier parentClassifier = getApiContext().getById(Classifier.class, classifierId);
		Classifier ret = ClassifierApi.createClassifier( getApiContext(), cl.getLabel(), cl.getDescription(), parentClassifier);
		ret = (Classifier) maybeAddMetadata(ret, cl);
		return createSuccessfulResponse(ret, "classifier.pageview.id");
	}

}

package ox.softeng.metadatacatalogue.restapi.services;


import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import ox.softeng.metadatacatalogue.api.DataModelComponentApi;
import ox.softeng.metadatacatalogue.domain.core.Annotation;
import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;
import ox.softeng.metadatacatalogue.domain.core.Classifier;
import ox.softeng.metadatacatalogue.domain.core.DataModelComponent;
import ox.softeng.metadatacatalogue.restapi.Secured;
import ox.softeng.metadatacatalogue.restapi.transport.ResponseDTO;
import ox.softeng.metadatacatalogue.restapi.transport.SearchParamsDTO;

@Path("/datamodelcomponent")
public class DataModelComponentService extends SharableService {
	
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
	
	@Path("/search")
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Secured(allowUnAuthenticated= true)
	public ObjectNode searchDataModel(SearchParamsDTO searchParams) throws Exception
	{
		
		long count = getApiContext().searchCount(DataModelComponent.class, searchParams.getSearchTerm());
		ArrayNode dcs =  getApiContext().searchMap(
				DataModelComponent.class, "datamodelcomponent.pageview.id", 
				searchParams.getSearchTerm(), searchParams.getOffset(), searchParams.getLimit());
		
		ObjectNode jn = JsonNodeFactory.instance.objectNode();
		jn.put("count", count);
		jn.set("results", dcs);
		return jn;
	}

	@Path("/newAnnotation/{id}")
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Secured(allowUnAuthenticated=false)
	public ResponseDTO addAnnotation(@PathParam("id") UUID dmcId, Annotation an) throws Exception
	{
		DataModelComponent dmc = getApiContext().getById(DataModelComponent.class, dmcId);

		Annotation ret = DataModelComponentApi.newAnnotation(getApiContext(), dmc, an.getLabel(), an.getDescription());

		return this.createSuccessfulResponse(ret, "annotation.pageView");
		
	}
		
}

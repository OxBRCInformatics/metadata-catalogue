package ox.softeng.metadatacatalogue.restapi.services;


import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ox.softeng.metadatacatalogue.api.CatalogueItemApi;
import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;
import ox.softeng.metadatacatalogue.domain.core.Metadata;
import ox.softeng.metadatacatalogue.restapi.Secured;
import ox.softeng.metadatacatalogue.restapi.transport.ResponseDTO;

public abstract class CatalogueItemService extends BasicCatalogueService {
	
	public static Class<? extends CatalogueItem> type = CatalogueItem.class;
	
	@Path("/addMetadata/{id}")
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Secured(allowUnAuthenticated= true)
	public ResponseDTO addMetadata(@PathParam("id") UUID dataModelId, Metadata metadataParam) throws Exception
	{
		CatalogueItem catalogueItem = getApiContext().getById(type, dataModelId);

		Metadata md = CatalogueItemApi.addMetadata(getApiContext(), catalogueItem, metadataParam.getKey(), metadataParam.getValue());

		return this.createSuccessfulResponse(md, "metadata.basic");
		
	}
		
}

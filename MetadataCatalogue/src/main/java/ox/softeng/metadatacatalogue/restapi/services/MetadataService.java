package ox.softeng.metadatacatalogue.restapi.services;

import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ox.softeng.metadatacatalogue.api.MetadataApi;
import ox.softeng.metadatacatalogue.domain.core.Metadata;
import ox.softeng.metadatacatalogue.restapi.Secured;
import ox.softeng.metadatacatalogue.restapi.transport.ResponseDTO;

@Path("/metadata")
public class MetadataService extends BasicCatalogueService {
	
	//public static Class<? extends CatalogueItem> type = Metadata.class;
	

	@Path("/edit/{id}")
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Secured(allowUnAuthenticated=false)
	public ResponseDTO newChildDataClass(@PathParam("id") UUID metadataId, Metadata md) throws Exception
	{		
		Metadata originalMD = getApiContext().getById(Metadata.class, metadataId);

		Metadata ret = MetadataApi.edit(getApiContext(), originalMD, md.getKey(), md.getValue());
		return createSuccessfulResponse(ret, "metadata.id");
	}
		
}

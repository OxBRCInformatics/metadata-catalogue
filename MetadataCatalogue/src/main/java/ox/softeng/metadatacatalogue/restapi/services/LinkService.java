package ox.softeng.metadatacatalogue.restapi.services;

import ox.softeng.metadatacatalogue.domain.core.Link;

import javax.ws.rs.Path;

import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;

@Path("/link")
public class LinkService extends SharableService {
	
	public static Class<? extends CatalogueItem> type = Link.class;
	

		
}

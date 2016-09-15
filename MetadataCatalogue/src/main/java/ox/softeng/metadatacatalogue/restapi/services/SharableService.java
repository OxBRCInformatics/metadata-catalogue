package ox.softeng.metadatacatalogue.restapi.services;


import javax.ws.rs.Path;

import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;
import ox.softeng.metadatacatalogue.domain.core.Sharable;

@Path("/sharable")
public class SharableService extends CatalogueItemService {
	
	public static Class<? extends CatalogueItem> type = Sharable.class;
	

		
}

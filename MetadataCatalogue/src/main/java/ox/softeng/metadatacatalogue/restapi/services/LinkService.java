package ox.softeng.metadatacatalogue.restapi.services;

import ox.softeng.metadatacatalogue.domain.core.Link;
import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;


public abstract class LinkService extends SharableService {
	
	public static Class<? extends CatalogueItem> type = Link.class;
	

		
}

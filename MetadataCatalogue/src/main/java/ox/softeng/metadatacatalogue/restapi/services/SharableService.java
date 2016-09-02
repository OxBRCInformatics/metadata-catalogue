package ox.softeng.metadatacatalogue.restapi.services;


import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;
import ox.softeng.metadatacatalogue.domain.core.Sharable;


public abstract class SharableService extends CatalogueItemService {
	
	public static Class<? extends CatalogueItem> type = Sharable.class;
	

		
}

package ox.softeng.metadatacatalogue.restapi.services;


import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;
import ox.softeng.metadatacatalogue.domain.core.DataModelComponent;


public abstract class DataModelComponentService extends SharableService {
	
	public static Class<? extends CatalogueItem> type = DataModelComponent.class;
	

		
}

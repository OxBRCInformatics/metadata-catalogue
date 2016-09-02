package ox.softeng.metadatacatalogue.restapi.services;

import ox.softeng.metadatacatalogue.domain.core.Database;
import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;


public abstract class DatabaseService extends DataModelService {
	
	public static Class<? extends CatalogueItem> type = Database.class;
	

		
}

package ox.softeng.metadatacatalogue.restapi.services;

import ox.softeng.metadatacatalogue.domain.core.DataStandard;
import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;


public abstract class DataStandardService extends DataModelService {
	
	public static Class<? extends CatalogueItem> type = DataStandard.class;
	

		
}

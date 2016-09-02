package ox.softeng.metadatacatalogue.restapi.services;

import ox.softeng.metadatacatalogue.domain.core.EnumerationType;
import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;


public abstract class EnumerationTypeService extends DataTypeService {
	
	public static Class<? extends CatalogueItem> type = EnumerationType.class;
	

		
}

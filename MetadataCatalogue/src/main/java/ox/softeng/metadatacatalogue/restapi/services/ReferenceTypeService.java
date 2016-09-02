package ox.softeng.metadatacatalogue.restapi.services;

import ox.softeng.metadatacatalogue.domain.core.ReferenceType;
import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;


public abstract class ReferenceTypeService extends DataTypeService {
	
	public static Class<? extends CatalogueItem> type = ReferenceType.class;
	

		
}

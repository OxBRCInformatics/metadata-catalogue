package ox.softeng.metadatacatalogue.restapi.services;

import ox.softeng.metadatacatalogue.domain.core.PrimitiveType;
import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;


public abstract class PrimitiveTypeService extends DataTypeService {
	
	public static Class<? extends CatalogueItem> type = PrimitiveType.class;
	

		
}

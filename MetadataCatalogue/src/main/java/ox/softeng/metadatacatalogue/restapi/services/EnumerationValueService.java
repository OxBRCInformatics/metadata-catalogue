package ox.softeng.metadatacatalogue.restapi.services;

import ox.softeng.metadatacatalogue.domain.core.EnumerationValue;
import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;


public abstract class EnumerationValueService extends DataModelComponentService {
	
	public static Class<? extends CatalogueItem> type = EnumerationValue.class;
	

		
}

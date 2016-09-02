package ox.softeng.metadatacatalogue.restapi.services;

import ox.softeng.metadatacatalogue.domain.core.DataType;
import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;


public abstract class DataTypeService extends DataModelComponentService {
	
	public static Class<? extends CatalogueItem> type = DataType.class;
	

		
}

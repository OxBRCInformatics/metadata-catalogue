package ox.softeng.metadatacatalogue.restapi.services;

import ox.softeng.metadatacatalogue.domain.core.DataSet;
import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;


public abstract class DataSetService extends DataModelService {
	
	public static Class<? extends CatalogueItem> type = DataSet.class;
	

		
}

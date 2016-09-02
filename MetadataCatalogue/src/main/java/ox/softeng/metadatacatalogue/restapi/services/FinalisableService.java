package ox.softeng.metadatacatalogue.restapi.services;

import ox.softeng.metadatacatalogue.domain.core.Finalisable;
import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;


public abstract class FinalisableService extends DataModelComponentService {
	
	public static Class<? extends CatalogueItem> type = Finalisable.class;
	

		
}

package ox.softeng.metadatacatalogue.restapi.services;

import ox.softeng.metadatacatalogue.domain.core.Form;
import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;


public abstract class FormService extends DataModelService {
	
	public static Class<? extends CatalogueItem> type = Form.class;
	

		
}

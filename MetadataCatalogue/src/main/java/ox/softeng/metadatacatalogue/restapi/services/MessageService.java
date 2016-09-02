package ox.softeng.metadatacatalogue.restapi.services;

import ox.softeng.metadatacatalogue.domain.core.Message;
import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;


public abstract class MessageService extends DataModelService {
	
	public static Class<? extends CatalogueItem> type = Message.class;
	

		
}

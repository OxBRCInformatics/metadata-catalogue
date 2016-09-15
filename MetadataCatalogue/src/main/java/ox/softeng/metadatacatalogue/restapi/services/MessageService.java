package ox.softeng.metadatacatalogue.restapi.services;

import ox.softeng.metadatacatalogue.domain.core.Message;

import javax.ws.rs.Path;

import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;


@Path("/message")
public class MessageService extends DataModelService {
	
	public static Class<? extends CatalogueItem> type = Message.class;
	

		
}

package ox.softeng.metadatacatalogue.restapi.services;

import ox.softeng.metadatacatalogue.domain.core.Finalisable;

import javax.ws.rs.Path;

import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;

@Path("/finalisable")
public class FinalisableService extends DataModelComponentService {
	
	public static Class<? extends CatalogueItem> type = Finalisable.class;
	

		
}

package ox.softeng.metadatacatalogue.restapi.services;

import ox.softeng.metadatacatalogue.domain.core.DataStandard;

import javax.ws.rs.Path;

import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;

@Path("/datastandard")
public class DataStandardService extends DataModelService {
	
	public static Class<? extends CatalogueItem> type = DataStandard.class;
	

		
}

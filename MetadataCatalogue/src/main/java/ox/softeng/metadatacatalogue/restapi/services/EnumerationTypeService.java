package ox.softeng.metadatacatalogue.restapi.services;

import ox.softeng.metadatacatalogue.domain.core.EnumerationType;

import javax.ws.rs.Path;

import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;

@Path("/enumerationtype")
public class EnumerationTypeService extends DataTypeService {
	
	public static Class<? extends CatalogueItem> type = EnumerationType.class;
	

		
}

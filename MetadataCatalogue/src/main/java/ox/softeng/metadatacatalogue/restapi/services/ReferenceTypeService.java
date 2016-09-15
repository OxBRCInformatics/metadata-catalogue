package ox.softeng.metadatacatalogue.restapi.services;

import ox.softeng.metadatacatalogue.domain.core.ReferenceType;

import javax.ws.rs.Path;

import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;

@Path("/referencetype")
public class ReferenceTypeService extends DataTypeService {
	
	public static Class<? extends CatalogueItem> type = ReferenceType.class;
	

		
}

package ox.softeng.metadatacatalogue.restapi.services;

import ox.softeng.metadatacatalogue.domain.core.PrimitiveType;

import javax.ws.rs.Path;

import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;

@Path("/primitivetype")
public class PrimitiveTypeService extends DataTypeService {
	
	public static Class<? extends CatalogueItem> type = PrimitiveType.class;
	

		
}

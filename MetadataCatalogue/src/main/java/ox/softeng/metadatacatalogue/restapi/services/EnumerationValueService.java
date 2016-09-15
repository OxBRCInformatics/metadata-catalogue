package ox.softeng.metadatacatalogue.restapi.services;

import ox.softeng.metadatacatalogue.domain.core.EnumerationValue;

import javax.ws.rs.Path;

import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;

@Path("/enumerationvalue")
public class EnumerationValueService extends DataModelComponentService {
	
	public static Class<? extends CatalogueItem> type = EnumerationValue.class;
	

		
}

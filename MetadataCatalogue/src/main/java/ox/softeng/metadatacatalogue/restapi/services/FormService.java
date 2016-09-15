package ox.softeng.metadatacatalogue.restapi.services;

import ox.softeng.metadatacatalogue.domain.core.Form;

import javax.ws.rs.Path;

import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;


@Path("/form")
public class FormService extends DataModelService {
	
	public static Class<? extends CatalogueItem> type = Form.class;
	

		
}

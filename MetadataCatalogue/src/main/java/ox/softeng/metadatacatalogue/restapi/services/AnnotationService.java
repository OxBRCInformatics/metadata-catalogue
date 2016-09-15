package ox.softeng.metadatacatalogue.restapi.services;


import javax.ws.rs.Path;

import ox.softeng.metadatacatalogue.domain.core.Annotation;
import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;

@Path("/annotation")
public class AnnotationService extends SharableService {
	
	public static Class<? extends CatalogueItem> type = Annotation.class;
	

		
}

package ox.softeng.metadatacatalogue.restapi.services;


import ox.softeng.metadatacatalogue.domain.core.Annotation;
import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;


public abstract class AnnotationService extends SharableService {
	
	public static Class<? extends CatalogueItem> type = Annotation.class;
	

		
}

package ox.softeng.metadatacatalogue.restapi.services;


import ox.softeng.metadatacatalogue.domain.core.Classifier;
import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;


public abstract class ClassifierService extends SharableService {
	
	public static Class<? extends CatalogueItem> type = Classifier.class;
	

		
}

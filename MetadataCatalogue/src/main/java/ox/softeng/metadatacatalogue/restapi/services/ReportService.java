package ox.softeng.metadatacatalogue.restapi.services;

import ox.softeng.metadatacatalogue.domain.core.Report;
import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;


public abstract class ReportService extends DataModelService {
	
	public static Class<? extends CatalogueItem> type = Report.class;
	

		
}

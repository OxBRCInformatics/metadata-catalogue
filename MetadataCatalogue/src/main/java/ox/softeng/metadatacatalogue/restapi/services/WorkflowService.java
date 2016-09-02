package ox.softeng.metadatacatalogue.restapi.services;

import ox.softeng.metadatacatalogue.domain.core.Workflow;
import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;


public abstract class WorkflowService extends DataModelService {
	
	public static Class<? extends CatalogueItem> type = Workflow.class;
	

		
}

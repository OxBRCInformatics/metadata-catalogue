package ox.softeng.metadatacatalogue.restapi.services;

import ox.softeng.metadatacatalogue.domain.core.Workflow;

import javax.ws.rs.Path;

import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;


@Path("/workflow")
public class WorkflowService extends DataModelService {
	
	public static Class<? extends CatalogueItem> type = Workflow.class;
	

		
}

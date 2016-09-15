package ox.softeng.metadatacatalogue.test.restapi.service;

import ox.softeng.metadatacatalogue.api.WorkflowApi;
import ox.softeng.metadatacatalogue.domain.core.Workflow;

public class WorkflowServiceIT extends DataModelServiceIT<Workflow> {
	@Override
	protected Workflow getInstance() throws Exception
	{
		return WorkflowApi.createWorkflow(apiCtx, "My test Workflow", "Workflow description", "Test Author", "Test Organization");
	}

	@Override
	protected String getServicePath()
	{
		return "/workflow";
	}

	@Override
	protected Class<? extends Workflow> getClazz()
	{
		return Workflow.class;
	}


}

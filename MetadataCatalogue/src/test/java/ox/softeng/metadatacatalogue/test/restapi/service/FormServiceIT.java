package ox.softeng.metadatacatalogue.test.restapi.service;

import ox.softeng.metadatacatalogue.domain.core.Form;

import ox.softeng.metadatacatalogue.api.FormApi;

public class FormServiceIT<ObjectType> extends DataModelServiceIT<Form> {

	@Override
	protected Form getInstance() throws Exception
	{
		return FormApi.createForm(apiCtx, "My test Form", "Form description", "Test Author", "Test Organization");
	}
	
	@Override
	protected String getServicePath()
	{
		return "/form";
	}

	@Override
	protected Class<? extends Form> getClazz()
	{
		return Form.class;
	}

	
}

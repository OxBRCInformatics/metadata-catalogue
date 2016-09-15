package ox.softeng.metadatacatalogue.test.restapi.service;

import ox.softeng.metadatacatalogue.api.DataSetApi;
import ox.softeng.metadatacatalogue.domain.core.Finalisable;

public class FinalisableServiceIT<ObjectType> extends DataModelComponentServiceIT<Finalisable> {

	@Override
	protected Finalisable getInstance() throws Exception
	{
		return DataSetApi.createDataSet(apiCtx, "My test Finalisable", "Finalisable description", "Test Author", "Test Organization");
	}
	
	@Override
	protected String getServicePath()
	{
		return "/finalisable";
	}

	@Override
	protected Class<? extends Finalisable> getClazz()
	{
		return Finalisable.class;
	}

	
	
}

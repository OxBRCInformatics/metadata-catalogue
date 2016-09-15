package ox.softeng.metadatacatalogue.test.restapi.service;

import ox.softeng.metadatacatalogue.api.DataStandardApi;
import ox.softeng.metadatacatalogue.domain.core.DataStandard;

public class DataStandardServiceIT extends DataModelServiceIT<DataStandard> {

	@Override
	protected DataStandard getInstance() throws Exception
	{
		return DataStandardApi.createDataStandard(apiCtx, "My test DataSet", "DataSet description", "Test Author", "Test Organization");
	}
	
	@Override
	protected String getServicePath()
	{
		return "/datastandard";
	}

	@Override
	protected Class<? extends DataStandard> getClazz()
	{
		return DataStandard.class;
	}
	
	
}

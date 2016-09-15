package ox.softeng.metadatacatalogue.test.restapi.service;

import ox.softeng.metadatacatalogue.api.DataSetApi;
import ox.softeng.metadatacatalogue.domain.core.DataSet;
import ox.softeng.metadatacatalogue.domain.core.EnumerationType;

public class EnumerationTypeServiceIT extends DataTypeServiceIT<EnumerationType> {

	@Override
	protected EnumerationType getInstance() throws Exception
	{
		DataSet ds = DataSetApi.createDataSet(apiCtx, "My test DataSet", "DataSet description", "Test Author", "Test Organization");
		EnumerationType et = DataSetApi.newEnumerationType(apiCtx, ds, "Test Enum Type", "Enum type");
		return et;
	}
	
	@Override
	protected String getServicePath()
	{
		return "/enumerationtype";
	}

	@Override
	protected Class<? extends EnumerationType> getClazz()
	{
		return EnumerationType.class;
	}


	
}

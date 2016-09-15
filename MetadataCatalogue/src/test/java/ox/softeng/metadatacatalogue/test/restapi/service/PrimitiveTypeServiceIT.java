package ox.softeng.metadatacatalogue.test.restapi.service;

import ox.softeng.metadatacatalogue.api.DataSetApi;
import ox.softeng.metadatacatalogue.domain.core.DataSet;
import ox.softeng.metadatacatalogue.domain.core.PrimitiveType;

public class PrimitiveTypeServiceIT extends DataTypeServiceIT<PrimitiveType> {

	@Override
	protected PrimitiveType getInstance() throws Exception
	{
		DataSet ds = DataSetApi.createDataSet(apiCtx, "My test DataSet", "DataSet description", "Test Author", "Test Organization");
		PrimitiveType pt = DataSetApi.newPrimitiveType(apiCtx, ds, "String", "String type", "");
		return pt;
	}
	
	@Override
	protected String getServicePath()
	{
		return "/primitivetype";
	}

	@Override
	protected Class<? extends PrimitiveType> getClazz()
	{
		return PrimitiveType.class;
	}

	
	
}

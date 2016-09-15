package ox.softeng.metadatacatalogue.test.restapi.service;

import ox.softeng.metadatacatalogue.api.DataSetApi;
import ox.softeng.metadatacatalogue.domain.core.DataSet;
import ox.softeng.metadatacatalogue.domain.core.DataType;
import ox.softeng.metadatacatalogue.domain.core.PrimitiveType;

public class DataTypeServiceIT<ObjectType> extends DataModelComponentServiceIT<DataType> {

	@Override
	protected DataType getInstance() throws Exception
	{
		DataSet ds = DataSetApi.createDataSet(apiCtx, "My test DataSet", "DataSet description", "Test Author", "Test Organization");
		PrimitiveType pt = DataSetApi.newPrimitiveType(apiCtx, ds, "String", "String type", "");
		return pt;
	}
	
	@Override
	protected String getServicePath()
	{
		return "/datatype";
	}

	@Override
	protected Class<? extends DataType> getClazz()
	{
		return DataType.class;
	}

	
}

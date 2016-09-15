package ox.softeng.metadatacatalogue.test.restapi.service;

import ox.softeng.metadatacatalogue.api.DataClassApi;
import ox.softeng.metadatacatalogue.api.DataModelApi;
import ox.softeng.metadatacatalogue.api.DataSetApi;
import ox.softeng.metadatacatalogue.domain.core.DataClass;
import ox.softeng.metadatacatalogue.domain.core.DataElement;
import ox.softeng.metadatacatalogue.domain.core.DataSet;
import ox.softeng.metadatacatalogue.domain.core.PrimitiveType;

public class DataElementServiceIT extends DataModelComponentServiceIT<DataElement> {


	@Override
	protected DataElement getInstance() throws Exception
	{
		DataSet ds = DataSetApi.createDataSet(apiCtx, "My test Sharable", "Sharable description", "Test Author", "Test Organization");
		DataClass dc = DataModelApi.newDataClass(apiCtx, ds, "class1", "class1desc");
		PrimitiveType pt = DataSetApi.newPrimitiveType(apiCtx, ds, "String", "String data type", "no units"); 
		return DataClassApi.newChildDataElement(apiCtx, dc, "My test DataElement", "My test DataElement description", pt);
	}
	
	@Override
	protected String getServicePath()
	{
		return "/dataelement";
	}

	@Override
	protected Class<? extends DataElement> getClazz()
	{
		return DataElement.class;
	}
	
	
}

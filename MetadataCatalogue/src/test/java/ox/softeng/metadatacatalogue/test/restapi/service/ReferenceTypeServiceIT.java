package ox.softeng.metadatacatalogue.test.restapi.service;


import ox.softeng.metadatacatalogue.api.DataSetApi;
import ox.softeng.metadatacatalogue.domain.core.DataClass;
import ox.softeng.metadatacatalogue.domain.core.DataSet;
import ox.softeng.metadatacatalogue.domain.core.ReferenceType;

public class ReferenceTypeServiceIT extends DataTypeServiceIT<ReferenceType> {


	@Override
	protected ReferenceType getInstance() throws Exception
	{
		DataSet ds = DataSetApi.createDataSet(apiCtx, "My test DataSet", "DataSet description", "Test Author", "Test Organization");
		DataClass dc = DataSetApi.newDataClass(apiCtx, ds, "Text Class", "Test Class description");
		ReferenceType rt = DataSetApi.newReferenceType(apiCtx, ds, "new Reference Type", "Reference Type description", dc);
		return rt;
	}
	
	@Override
	protected String getServicePath()
	{
		return "/referencetype";
	}

	@Override
	protected Class<? extends ReferenceType> getClazz()
	{
		return ReferenceType.class;
	}


	
}

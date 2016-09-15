package ox.softeng.metadatacatalogue.test.restapi.service;

import ox.softeng.metadatacatalogue.api.DataSetApi;
import ox.softeng.metadatacatalogue.api.EnumerationTypeApi;
import ox.softeng.metadatacatalogue.domain.core.DataSet;
import ox.softeng.metadatacatalogue.domain.core.EnumerationType;
import ox.softeng.metadatacatalogue.domain.core.EnumerationValue;

public class EnumerationValueServiceIT extends DataModelComponentServiceIT<EnumerationValue> {

	@Override
	protected EnumerationValue getInstance() throws Exception
	{
		DataSet ds = DataSetApi.createDataSet(apiCtx, "My test DataSet", "DataSet description", "Test Author", "Test Organization");
		EnumerationType et = DataSetApi.newEnumerationType(apiCtx, ds, "Test Enum Type", "Enum type");
		EnumerationValue ev = EnumerationTypeApi.newEnumerationValue(apiCtx, et, "My test Key", "My test Value");
		return ev;
	}
	
	@Override
	protected String getServicePath()
	{
		return "/enumerationvalue";
	}

	@Override
	protected Class<? extends EnumerationValue> getClazz()
	{
		return EnumerationValue.class;
	}

	
}

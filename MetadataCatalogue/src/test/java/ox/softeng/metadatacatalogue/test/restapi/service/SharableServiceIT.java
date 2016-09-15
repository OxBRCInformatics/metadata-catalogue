package ox.softeng.metadatacatalogue.test.restapi.service;

import ox.softeng.metadatacatalogue.api.DataSetApi;
import ox.softeng.metadatacatalogue.domain.core.Sharable;

public class SharableServiceIT<ObjectType> extends CatalogueItemServiceIT<Sharable> {
	
	@Override
	protected Sharable getInstance() throws Exception
	{
		return DataSetApi.createDataSet(apiCtx, "My test Sharable", "Sharable description", "Test Author", "Test Organization");
	}
	
	@Override
	protected String getServicePath()
	{
		return "/sharable";
	}

	@Override
	protected Class<? extends Sharable> getClazz()
	{
		return Sharable.class;
	}

	
	
}

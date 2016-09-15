package ox.softeng.metadatacatalogue.test.restapi.service;

import ox.softeng.metadatacatalogue.api.DataSetApi;
import ox.softeng.metadatacatalogue.api.LinkApi;
import ox.softeng.metadatacatalogue.domain.core.DataSet;
import ox.softeng.metadatacatalogue.domain.core.Link;

public class LinkServiceIT<ObjectType> extends SharableServiceIT<Link> {

	@Override
	protected Link getInstance() throws Exception
	{
		DataSet sourceDS = DataSetApi.createDataSet(apiCtx, "My test source dataset", "My test source dataset description", "Test Author", "Test Organization");
		DataSet targetDS = DataSetApi.createDataSet(apiCtx, "My test target dataset", "My test target dataset description", "Test Author", "Test Organization");
		return LinkApi.createLink(apiCtx, "My test Link", "My test Link description", sourceDS, targetDS);
	}
	
	@Override
	protected String getServicePath()
	{
		return "/link";
	}

	@Override
	protected Class<? extends Link> getClazz()
	{
		return Link.class;
	}
	
	
	
}

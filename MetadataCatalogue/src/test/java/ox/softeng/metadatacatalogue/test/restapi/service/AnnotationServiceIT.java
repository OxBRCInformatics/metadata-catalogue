package ox.softeng.metadatacatalogue.test.restapi.service;

import ox.softeng.metadatacatalogue.api.DataSetApi;
import ox.softeng.metadatacatalogue.domain.core.Annotation;
import ox.softeng.metadatacatalogue.domain.core.DataSet;

public class AnnotationServiceIT<ObjectType> extends SharableServiceIT<Annotation> {

	@Override
	protected Annotation getInstance() throws Exception
	{
		DataSet ds = DataSetApi.createDataSet(apiCtx, "My test Sharable", "Sharable description", "Test Author", "Test Organization");
		Annotation ann = DataSetApi.newAnnotation(apiCtx, ds, "Annotation Title", "Annotation Description");
		return ann;
	}
	
	@Override
	protected String getServicePath()
	{
		return "/annotation";
	}

	@Override
	protected Class<? extends Annotation> getClazz()
	{
		return Annotation.class;
	}

}

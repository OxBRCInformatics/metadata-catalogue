package ox.softeng.metadatacatalogue.test.restapi.service;

import static org.junit.Assert.assertTrue;

import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

import ox.softeng.metadatacatalogue.api.ClassifierApi;
import ox.softeng.metadatacatalogue.domain.core.Classifier;

public class ClassifierServiceIT<ObjectType> extends SharableServiceIT<Classifier> {

	
	@Override
	protected Classifier getInstance() throws Exception
	{
		return ClassifierApi.createClassifier(apiCtx, "My Test Classifier", "My test classifier description");
	}
	
	@Override
	protected String getServicePath()
	{
		return "/classifier";
	}

	@Override
	protected Class<? extends Classifier> getClazz()
	{
		return Classifier.class;
	}
	
	@FlywayTest(invokeCleanDB=true, invokeBaselineDB=true)
	@Test
	public void createClassifier() throws Exception {
		
		LoginResponse lr = doLogin();

		Classifier cl = new Classifier();
		cl.setLabel("my new classifier");
		cl.setDescription("my classifier description");
		
		Classifier clReturned = assertSuccessfulPost("/classifier/create/", lr.cookie, cl, Classifier.class);

		assertTrue(clReturned.getLabel().equalsIgnoreCase("my new classifier"));
		assertTrue(clReturned.getDescription().equalsIgnoreCase("my classifier description"));
		assertTrue(clReturned.getId() != null);
		
		JsonNode jn = apiCtx.getByIdMap(Classifier.class, "classifier.pageview.id", clReturned.getId());
		Classifier cl2 = objectMapper.treeToValue(jn, Classifier.class);
		
		assertTrue(cl2.getLabel().equalsIgnoreCase("my new classifier"));

	}

	
}

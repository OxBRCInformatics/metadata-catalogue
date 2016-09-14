package ox.softeng.metadatacatalogue.test.domain.core;

import static org.junit.Assert.assertTrue;

import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Test;

import ox.softeng.metadatacatalogue.api.ClassifierApi;
import ox.softeng.metadatacatalogue.domain.core.Classifier;

public class ClassifierTest extends DatabaseTest{

	@FlywayTest(invokeCleanDB=true, invokeBaselineDB=true)
	@Test
	public void createClassifier() throws Exception
	{
		Classifier cl = ClassifierApi.createClassifier(apiCtx, "My first classifier", "classifier description");
		
		assertTrue(cl.getId() != null);
		Classifier cl2 = apiCtx.getById(Classifier.class, cl.getId());
		assertTrue(cl2.getLabel().equals("My first classifier"));
		assertTrue(cl2.getDescription().equals("classifier description"));
		
	}

	
	
}

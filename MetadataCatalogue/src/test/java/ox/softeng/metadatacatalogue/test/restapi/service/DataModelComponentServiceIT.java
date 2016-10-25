package ox.softeng.metadatacatalogue.test.restapi.service;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;


import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

import ox.softeng.metadatacatalogue.api.ClassifierApi;
import ox.softeng.metadatacatalogue.api.DataSetApi;
import ox.softeng.metadatacatalogue.domain.core.Annotation;
import ox.softeng.metadatacatalogue.domain.core.Classifier;
import ox.softeng.metadatacatalogue.domain.core.DataModelComponent;

public class DataModelComponentServiceIT <ObjectType> extends SharableServiceIT<DataModelComponent> {

	@Override
	protected DataModelComponent getInstance() throws Exception
	{
		return DataSetApi.createDataSet(apiCtx, "My test DataModelComponent", "My test DataModelComponent description", "Test Author", "Test Organization");
	}
	
	@Override
	protected String getServicePath()
	{
		return "/datamodelcomponent";
	}

	@Override
	protected Class<? extends DataModelComponent> getClazz()
	{
		return DataModelComponent.class;
	}
		
	@FlywayTest(invokeCleanDB=true, invokeBaselineDB=true)
	@Test
	public void addClassifier() throws Exception {
		
		LoginResponse lr = doLogin();
		DataModelComponent dmc = getInstance();
		
		Classifier cl = ClassifierApi.createClassifier(apiCtx, "my new classifier", "new classifier label");
		
		Classifier clParam = new Classifier();
		clParam.setId(cl.getId());
		
		String path = getServicePath() + "/addClassifier/" + dmc.getId();
		
		Classifier clReturned = assertSuccessfulPost(path, lr.cookie, clParam, Classifier.class);
		
		assertTrue(clReturned.getId() != null);
		assertTrue(clReturned.getLabel().equalsIgnoreCase("my new classifier"));
		assertTrue(clReturned.getDescription().equalsIgnoreCase("new classifier label"));
		
		JsonNode jn = apiCtx.getByIdMap(getClazz(), "datamodel.pageview.id", dmc.getId());
		
		DataModelComponent newDMC = objectMapper.treeToValue(jn,  getClazz());
		
		Set<Classifier> cls = newDMC.getClassifiers();
		assertTrue(cls.size() == 1);
		assertTrue(((Classifier)(cls.toArray())[0]).getLabel().equalsIgnoreCase("my new classifier"));
		assertTrue(((Classifier)(cls.toArray())[0]).getDescription().equalsIgnoreCase("new classifier label"));
		doLogout(lr.cookie);
	}
	
	@FlywayTest(invokeCleanDB=true, invokeBaselineDB=true)
	@Test
	public void addAnnotation() throws Exception {
		
		LoginResponse lr = doLogin();
		DataModelComponent dmc = getInstance();
		
		Annotation an = new Annotation();
		an.setLabel("My first annotation");
		an.setDescription("description");
		
		String path = getServicePath() + "/newAnnotation/" + dmc.getId();
		
		Annotation anReturned = assertSuccessfulPost(path, lr.cookie, an, Annotation.class);
		
		assertTrue(anReturned.getId() != null);
		assertTrue(anReturned.getLabel().equalsIgnoreCase("My first annotation"));
		assertTrue(anReturned.getDescription().equalsIgnoreCase("description"));
		
		JsonNode jn = apiCtx.getByIdMap(getClazz(), "datamodel.pageview.id", dmc.getId());
		
		DataModelComponent newDMC = objectMapper.treeToValue(jn,  getClazz());
		
		List<Annotation> ans = newDMC.getAnnotations();
		assertTrue(ans.size() == 1);
		assertTrue((ans.get(0)).getLabel().equalsIgnoreCase("My first annotation"));
		assertTrue((ans.get(0)).getDescription().equalsIgnoreCase("description"));
		doLogout(lr.cookie);
	}

}

package ox.softeng.metadatacatalogue.test.restapi.service;

import static org.junit.Assert.assertTrue;

import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

import ox.softeng.metadatacatalogue.api.DataSetApi;
import ox.softeng.metadatacatalogue.domain.core.DataModel;
import ox.softeng.metadatacatalogue.domain.core.DataSet;

public class DataSetServiceIT<ObjectType> extends DataModelServiceIT<DataSet> {

	@Override
	protected DataModel getInstance() throws Exception
	{
		return DataSetApi.createDataSet(apiCtx, "My test DataSet", "DataSet description", "Test Author", "Test Organization");
	}
	
	@Override
	protected String getServicePath()
	{
		return "/dataset";
	}

	@Override
	protected Class<? extends DataSet> getClazz()
	{
		return DataSet.class;
	}
	
	
	
	@FlywayTest(invokeCleanDB=true, invokeBaselineDB=true)
	@Test
	public void createDataSet() throws Exception {
		
		LoginResponse lr = doLogin();
		
		DataSet ds = new DataSet();
		ds.setLabel("Dataset 1");
		ds.setDescription("description 1");
		ds.setAuthor("author 1");
		ds.setOrganization("organisation 1");

		DataSet dsReturned = assertSuccessfulPost("/dataset/create/", lr.cookie, ds, DataSet.class);

		assertTrue(dsReturned.getLabel().equalsIgnoreCase("Dataset 1"));
		
		JsonNode jn = apiCtx.getByIdMap(DataSet.class, "datamodel.pageview.id", dsReturned.getId());
		DataSet dm = objectMapper.treeToValue(jn, DataSet.class);
		
		assertTrue(dm.getLabel().equalsIgnoreCase("Dataset 1"));
		doLogout(lr.cookie);
	}

	
}

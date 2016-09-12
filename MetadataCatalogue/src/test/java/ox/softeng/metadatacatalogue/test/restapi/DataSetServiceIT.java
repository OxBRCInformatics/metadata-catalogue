package ox.softeng.metadatacatalogue.test.restapi;

import static org.junit.Assert.assertTrue;

import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

import ox.softeng.metadatacatalogue.api.DataSetApi;
import ox.softeng.metadatacatalogue.domain.core.DataModel;
import ox.softeng.metadatacatalogue.domain.core.DataSet;

public class DataSetServiceIT extends DataModelServiceIT {

	protected DataModel getDataModel() throws Exception
	{
		DataSet ds = DataSetApi.createDataSet(apiCtx, "Test Dataset", "This is just a test dataset", "Author", "Organization");
		return ds;
	}
	
	protected String getServicePath()
	{
		return "/dataset";
	}
	static Class<? extends DataModel> subType = DataSet.class;

	
	
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

	}

	
}

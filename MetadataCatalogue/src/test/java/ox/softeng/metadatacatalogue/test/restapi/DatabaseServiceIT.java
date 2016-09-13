package ox.softeng.metadatacatalogue.test.restapi;

import static org.junit.Assert.assertTrue;

import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

import ox.softeng.metadatacatalogue.api.DatabaseApi;
import ox.softeng.metadatacatalogue.domain.core.DataModel;
import ox.softeng.metadatacatalogue.domain.core.DataSet;
import ox.softeng.metadatacatalogue.domain.core.Database;

public class DatabaseServiceIT extends DataModelServiceIT {

	protected DataModel getDataModel() throws Exception
	{
		Database ds = DatabaseApi.createDatabase(apiCtx, "Test Dataset", "This is just a test dataset", "Author", "Organization", "mysql");
		return ds;
	}
	
	protected String getServicePath()
	{
		return "/database";
	}
	static Class<? extends DataModel> subType = Database.class;

	
	
	@FlywayTest(invokeCleanDB=true, invokeBaselineDB=true)
	@Test
	public void createDatabase() throws Exception {
		
		LoginResponse lr = doLogin();
		
		Database db = new Database();
		db.setLabel("Database 1");
		db.setDescription("description 1");
		db.setAuthor("author 1");
		db.setOrganization("organisation 1");
		db.setDialect("mysql");

		Database dbReturned = assertSuccessfulPost("/database/create/", lr.cookie, db, Database.class);

		assertTrue(dbReturned.getLabel().equalsIgnoreCase("Database 1"));
		
		JsonNode jn = apiCtx.getByIdMap(Database.class, "datamodel.pageview.id", dbReturned.getId());
		Database dm = objectMapper.treeToValue(jn, Database.class);
		
		assertTrue(dm.getLabel().equalsIgnoreCase("Database 1"));

	}

	
}

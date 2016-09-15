package ox.softeng.metadatacatalogue.test.restapi.service;

import static org.junit.Assert.assertTrue;

import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

import ox.softeng.metadatacatalogue.api.DatabaseApi;
import ox.softeng.metadatacatalogue.domain.core.DataModel;
import ox.softeng.metadatacatalogue.domain.core.Database;

public class DatabaseServiceIT extends DataModelServiceIT<Database> {

	@Override
	protected DataModel getInstance() throws Exception
	{
		return DatabaseApi.createDatabase(apiCtx, "My test DataSet", "DataSet description", "Test Author", "Test Organization", "mysql");
	}
	
	@Override
	protected String getServicePath()
	{
		return "/database";
	}

	@Override
	protected Class<? extends Database> getClazz()
	{
		return Database.class;
	}
	
	
	
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
		doLogout(lr.cookie);

	}

	
}

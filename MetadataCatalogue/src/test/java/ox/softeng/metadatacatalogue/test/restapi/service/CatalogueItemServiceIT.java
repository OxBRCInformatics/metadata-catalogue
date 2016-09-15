package ox.softeng.metadatacatalogue.test.restapi.service;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

import ox.softeng.metadatacatalogue.api.DataSetApi;
import ox.softeng.metadatacatalogue.domain.core.CatalogueItem;
import ox.softeng.metadatacatalogue.domain.core.Metadata;
import ox.softeng.metadatacatalogue.test.restapi.APITest;

public class CatalogueItemServiceIT <ObjectType> extends APITest<CatalogueItem> {

	@Override
	protected CatalogueItem getInstance() throws Exception
	{
		return DataSetApi.createDataSet(apiCtx, "My test CatalogueItem", "CatalogueItem  description", "Test Author", "Test Organization");
	}
	
	@Override
	protected String getServicePath()
	{
		return "/catalogueitem";
	}

	@Override
	protected Class<? extends CatalogueItem> getClazz()
	{
		return CatalogueItem.class;
	}
	

	
	@FlywayTest(invokeCleanDB=true, invokeBaselineDB=true)
	@Test
	public void addMetadata() throws Exception {
		
		LoginResponse lr = doLogin();
		CatalogueItem ci = getInstance();
		
		Metadata md = new Metadata();
		md.setKey("key1");
		md.setValue("value1");
		
		String path = getServicePath() + "/addMetadata/" + ci.getId();
		
		Metadata mdReturned = assertSuccessfulPost(path, lr.cookie, md, Metadata.class);
		
		assertTrue(mdReturned.getId() != null);
		assertTrue(mdReturned.getKey().equalsIgnoreCase("key1"));
		assertTrue(mdReturned.getValue().equalsIgnoreCase("value1"));
		
		JsonNode jn = apiCtx.getByIdMap(getClazz(), "datamodel.pageview.id", ci.getId());
		
		CatalogueItem newCI = objectMapper.treeToValue(jn,  getClazz());
		
		List<Metadata> mds = newCI.getMetadata();
		assertTrue(mds.size() == 1);
		assertTrue(mds.get(0).getKey().equalsIgnoreCase("key1"));
		assertTrue(mds.get(0).getValue().equalsIgnoreCase("value1"));

	}
	
}

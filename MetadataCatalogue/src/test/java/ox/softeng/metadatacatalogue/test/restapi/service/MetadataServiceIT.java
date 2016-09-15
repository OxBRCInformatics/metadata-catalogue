package ox.softeng.metadatacatalogue.test.restapi.service;

import static org.junit.Assert.assertTrue;

import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Test;

import ox.softeng.metadatacatalogue.api.DataSetApi;
import ox.softeng.metadatacatalogue.domain.core.DataSet;
import ox.softeng.metadatacatalogue.domain.core.Metadata;
import ox.softeng.metadatacatalogue.test.restapi.APITest;

public class MetadataServiceIT extends APITest<Metadata> {

	/* These methods are not really needed for this service - we'll give some default implementations */
	
	@Override
	protected Metadata getInstance() throws Exception {
		return null;
	}


	@Override
	protected String getServicePath() {
		return "/metadata";
	}


	@Override
	protected Class<? extends Metadata> getClazz() {
		return Metadata.class;
	}
	
	@FlywayTest(invokeCleanDB=true, invokeBaselineDB=true)
	@Test
	public void editMetadata() throws Exception {
		
		LoginResponse lr = doLogin();
		
		DataSet ds = DataSetApi.createDataSet(apiCtx, "My test DataSet", "DataSet description", "Test Author", "Test Organization");
		Metadata md = DataSetApi.addMetadata(apiCtx, ds, "original key", "original value");
		Metadata newMetadata = new Metadata();
		newMetadata.setKey("new key");
		newMetadata.setValue("new value");
		
		Metadata mdReturned = assertSuccessfulPost("/metadata/edit/" + md.getId(), lr.cookie, newMetadata, Metadata.class);

		assertTrue(mdReturned.getKey().equalsIgnoreCase("new key"));
		assertTrue(mdReturned.getValue().equalsIgnoreCase("new value"));
		doLogout(lr.cookie);
	}

	
	
	
}

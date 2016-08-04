package ox.softeng.metadatacatalogue.test.domain.core;

import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Assert;
import org.junit.Test;

import ox.softeng.metadatacatalogue.api.DataSetApi;
import ox.softeng.metadatacatalogue.domain.core.DataSet;

public class DataSetTest extends DatabaseTest {

	
	@FlywayTest(invokeCleanDB=true, invokeBaselineDB=true)
	@Test
	public void createDataSetTest() throws Exception
	{
		createDataSet();
		List<DataSet> dss = DataSetApi.getAllDataSets(apiCtx);
		assertTrue(dss!= null && dss.size() == 1);
		DataSet ds = dss.get(0);

		assertTrue(ds!=null);
		assertTrue(ds.getLabel().equals("Label"));
		assertTrue(ds.getDescription().equals("Description"));
		assertTrue(ds.getAuthor().equals("James Welch"));
		assertTrue(ds.getOrganization().equals("My Org"));
		assertTrue(ds.getType().equals("DataSet"));
		assertTrue(ds.getDtype().equals("ox.softeng.metadatacatalogue.domain.core.DataSet"));
		
	}

	
	private DataSet createDataSet() throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		DataSet ds = null;
		try {
			ds = DataSetApi.createDataSet(apiCtx, "Label", "Description", "James Welch", "My Org" );
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
		assertTrue(ds!=null);
		return ds;
	}
	
	
}

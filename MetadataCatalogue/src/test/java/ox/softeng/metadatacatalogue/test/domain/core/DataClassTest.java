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

public class DataClassTest extends DatabaseTest {

	
	@FlywayTest(invokeCleanDB=true, invokeBaselineDB=true)
	@Test
	public void createDataClassTest() throws Exception
	{
		DataSet ds = createDataSet();
		assertTrue(ds.getLabel().equalsIgnoreCase("Label"));

		List<DataSet> dss = DataSetApi.getAllDataSets(apiCtx);
		assertTrue(dss!= null && dss.size() == 1);
		//DataSet ds = dss.get(0);

		
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

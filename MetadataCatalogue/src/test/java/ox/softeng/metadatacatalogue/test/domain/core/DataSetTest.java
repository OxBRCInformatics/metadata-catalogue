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
import ox.softeng.metadatacatalogue.domain.core.Metadata;

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
		assertTrue(ds.getDtype().equals("DataSet"));
		
	}

	@FlywayTest(invokeCleanDB=true, invokeBaselineDB=true)
	@Test
	public void EditDataSetTest() throws Exception
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
		assertTrue(ds.getDtype().equals("DataSet"));

		DataSetApi.editDetails(apiCtx, ds, "Label 2", "Description 2");
		dss = DataSetApi.getAllDataSets(apiCtx);
		assertTrue(dss!= null && dss.size() == 1);
		ds = dss.get(0);
		assertTrue(ds!=null);
		assertTrue(ds.getLabel().equals("Label 2"));
		assertTrue(ds.getDescription().equals("Description 2"));
		assertTrue(ds.getAuthor().equals("James Welch"));
		assertTrue(ds.getOrganization().equals("My Org"));
		assertTrue(ds.getType().equals("DataSet"));
		assertTrue(ds.getDtype().equals("DataSet"));
		
		
	}

	
	@FlywayTest(invokeCleanDB=true, invokeBaselineDB=true)
	@Test
	public void AddMetadataTest() throws Exception
	{
		DataSet ds1 = createDataSet();
		DataSetApi.addMetadata(apiCtx, ds1, "Created For", "Test Purposes");
		DataSetApi.addMetadata(apiCtx, ds1, "Test Metadata", "Value 2");

		
		List<DataSet> dss = DataSetApi.getAllDataSets(apiCtx);
		assertTrue(dss!= null && dss.size() == 1);
		DataSet ds2 = dss.get(0);

		assertTrue(ds2!=null);
		assertTrue(ds2.getLabel().equals("Label"));
		List<Metadata> md = ds2.getMetadata();
		System.err.println("Metadata size: " + md.size());
		for(Metadata m : md)
		{
			System.err.println("m:" + m.getKey());
		}
		assertTrue(md.size() == 2);
		assertTrue(md.get(0).getKey().equals("Created For"));
		assertTrue(md.get(1).getKey().equals("Test Metadata"));
		assertTrue(md.get(0).getValue().equals("Test Purposes"));
		assertTrue(md.get(1).getValue().equals("Value 2"));
		
		
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

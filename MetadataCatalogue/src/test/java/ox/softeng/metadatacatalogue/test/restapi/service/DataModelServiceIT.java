package ox.softeng.metadatacatalogue.test.restapi.service;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Set;


import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

import ox.softeng.metadatacatalogue.api.DataModelApi;
import ox.softeng.metadatacatalogue.api.DataSetApi;
import ox.softeng.metadatacatalogue.domain.core.DataClass;
import ox.softeng.metadatacatalogue.domain.core.DataModel;
import ox.softeng.metadatacatalogue.domain.core.DataType;
import ox.softeng.metadatacatalogue.domain.core.EnumerationType;
import ox.softeng.metadatacatalogue.domain.core.EnumerationValue;
import ox.softeng.metadatacatalogue.domain.core.PrimitiveType;
import ox.softeng.metadatacatalogue.domain.core.ReferenceType;

public class DataModelServiceIT<ObjectType> extends FinalisableServiceIT<DataModel> {


	@Override
	protected DataModel getInstance() throws Exception
	{
		return DataSetApi.createDataSet(apiCtx, "My test DataModel", "DataModel description", "Test Author", "Test Organization");
	}
	
	@Override
	protected String getServicePath()
	{
		return "/datamodel";
	}

	@Override
	protected Class<? extends DataModel> getClazz()
	{
		return DataModel.class;
	}


	@FlywayTest(invokeCleanDB=true, invokeBaselineDB=true)
	@Test
	public void newChildDataClass() throws Exception {
		
		LoginResponse lr = doLogin();
		
		DataModel ds = getInstance();
		DataClass dc = new DataClass();
		dc.setLabel("my test class");
		dc.setDescription("my test class description");
		
		String path = getServicePath() + "/newChildDataClass/" + ds.getId();
		
		DataClass dcReturned = assertSuccessfulPost(path, lr.cookie, dc, DataClass.class);
		
		assertTrue(dcReturned.getId() != null);
		assertTrue(dcReturned.getLabel().equalsIgnoreCase("my test class"));
		assertTrue(dcReturned.getDescription().equalsIgnoreCase("my test class description"));
		
		JsonNode jn = apiCtx.getByIdMap(getClazz(), "datamodel.pageview.id", ds.getId());
		DataModel newDM = objectMapper.treeToValue(jn, getClazz());
		
		List<DataClass> childDataClasses = newDM.getChildDataClasses();

		assertTrue(childDataClasses.size() == 1);
		assertTrue(childDataClasses.get(0).getLabel().equalsIgnoreCase("my test class"));
		assertTrue(childDataClasses.get(0).getDescription().equalsIgnoreCase("my test class description"));
		doLogout(lr.cookie);
	}

	@FlywayTest(invokeCleanDB=true, invokeBaselineDB=true)
	@Test
	public void newPrimitiveDataType() throws Exception {
		
		LoginResponse lr = doLogin();
		
		DataModel dm = getInstance();

		PrimitiveType pt = new PrimitiveType();
		pt.setLabel("my test pt");
		pt.setDescription("test pt description");
		pt.setUnits("units");
		
		String path = getServicePath() + "/newPrimitiveDataType/" + dm.getId();
		PrimitiveType ptReturned = assertSuccessfulPost(path, lr.cookie, pt, PrimitiveType.class);
		
		assertTrue(ptReturned.getLabel().equalsIgnoreCase("my test pt"));
		assertTrue(ptReturned.getDescription().equalsIgnoreCase("test pt description"));
		assertTrue(ptReturned.getId() != null);
		
		JsonNode jn = apiCtx.getByIdMap(getClazz(), "datamodel.pageview.id", dm.getId());
		DataModel newDS = objectMapper.treeToValue(jn, getClazz());
		Set<DataType> dts = newDS.getOwnedDataTypes();
		
		assertTrue(dts.size() == 1);
		assertTrue(((DataType)(dts.toArray())[0]).getLabel().equalsIgnoreCase("my test pt"));
		assertTrue(((DataType)(dts.toArray())[0]).getDescription().equalsIgnoreCase("test pt description"));
		doLogout(lr.cookie);
	}

	@FlywayTest(invokeCleanDB=true, invokeBaselineDB=true)
	@Test
	public void newReferenceDataType() throws Exception {
		
		LoginResponse lr = doLogin();
		
		DataModel ds = getInstance();
		DataClass dc = DataModelApi.newDataClass(apiCtx, ds, "class1", "class1desc");
		ReferenceType newRefType = new ReferenceType();
		newRefType.setLabel("my test rt");
		newRefType.setDescription("test rt description");
		DataClass refDC = new DataClass();
		refDC.setId(dc.getId());
		newRefType.setReferenceClass(refDC);
		
		String path = getServicePath() + "/newReferenceDataType/" + ds.getId();
		ReferenceType rtReturned = assertSuccessfulPost(path, lr.cookie, newRefType, ReferenceType.class);	

		assertTrue(rtReturned.getLabel().equalsIgnoreCase("my test rt"));
		assertTrue(rtReturned.getDescription().equalsIgnoreCase("test rt description"));
		assertTrue(rtReturned.getReferenceClass() != null);
		assertTrue(rtReturned.getReferenceClass().getId().equals(dc.getId()));

		JsonNode jn = apiCtx.getByIdMap(getClazz(), "datamodel.pageview.id", ds.getId());
		
		DataModel newDS = objectMapper.treeToValue(jn, getClazz());
		Set<DataType> dts = newDS.getOwnedDataTypes();
		
		assertTrue(dts.size() == 1);
		assertTrue(((DataType)(dts.toArray())[0]).getLabel().equalsIgnoreCase("my test rt"));
		assertTrue(((DataType)(dts.toArray())[0]).getDescription().equalsIgnoreCase("test rt description"));
		doLogout(lr.cookie);
	}

	@FlywayTest(invokeCleanDB=true, invokeBaselineDB=true)
	@Test
	public void newEnumerationDataType() throws Exception {
		
		LoginResponse lr = doLogin();
		
		DataModel ds = getInstance();
		
		String path = getServicePath() + "/newEnumerationDataType/" + ds.getId();
		
		EnumerationType et = new EnumerationType();
		et.setLabel("my test et");
		et.setDescription("test et description");
		EnumerationValue ev1 = new EnumerationValue();
		ev1.setKey("key 1");
		ev1.setValue("value 1");
		EnumerationValue ev2 = new EnumerationValue();
		ev2.setKey("key 2");
		ev2.setValue("value 2");
		et.setEnumerationValues(Arrays.asList(ev1, ev2));
		
		EnumerationType etReturned = assertSuccessfulPost(path, lr.cookie, et, EnumerationType.class);
		
		assertTrue(etReturned.getLabel().equalsIgnoreCase("my test et"));
		assertTrue(etReturned.getDescription().equalsIgnoreCase("test et description"));
		assertTrue(etReturned.getId() != null);
		
		JsonNode jn = apiCtx.getByIdMap(getClazz(), "datamodel.pageview.id", ds.getId());
		DataModel newDS = objectMapper.treeToValue(jn, getClazz());
		Set<DataType> dts = newDS.getOwnedDataTypes();
		assertTrue(newDS.getOwnedDataTypes().size() == 1);
		assertTrue(((DataType)(dts.toArray())[0]).getLabel().equalsIgnoreCase("my test et"));
		assertTrue(((DataType)(dts.toArray())[0]).getDescription().equalsIgnoreCase("test et description"));
		doLogout(lr.cookie);
	}
	
	@FlywayTest(invokeCleanDB=true, invokeBaselineDB=true)
	@Test
	public void testImportExport() throws Exception {
		
		LoginResponse lr = doLogin();
		
		DataModel ds = getInstance();
		
		String path = getServicePath() + "/export/1.0/" + ds.getId();
		

		doLogout(lr.cookie);
	}


}

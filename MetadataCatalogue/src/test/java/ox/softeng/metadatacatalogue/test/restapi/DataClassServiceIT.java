package ox.softeng.metadatacatalogue.test.restapi;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

import ox.softeng.metadatacatalogue.api.DataModelApi;
import ox.softeng.metadatacatalogue.api.DataSetApi;
import ox.softeng.metadatacatalogue.domain.core.DataClass;
import ox.softeng.metadatacatalogue.domain.core.DataElement;
import ox.softeng.metadatacatalogue.domain.core.DataSet;
import ox.softeng.metadatacatalogue.domain.core.DataType;
import ox.softeng.metadatacatalogue.domain.core.Metadata;
import ox.softeng.metadatacatalogue.restapi.services.DataClassService;
import ox.softeng.metadatacatalogue.restapi.services.DataClassService.NewDataElementDTO.DataTypeDTO;

public class DataClassServiceIT extends APITest {

	@FlywayTest(invokeCleanDB=true, invokeBaselineDB=true)
	@Test
	public void addMetadata() throws Exception {
		
		LoginResponse lr = doLogin();
		
		DataSet ds = DataSetApi.createDataSet(apiCtx, "Test Dataset", "This is just a test dataset", "Author", "Organization");
		DataClass dc = DataModelApi.newDataClass(apiCtx, ds, "class1", "class1desc");
		Metadata md = new Metadata();
		md.setKey("key1");
		md.setValue("value1");
		
		String path = "/dataclass/addMetadata/" + dc.getId();
		
		Metadata mdReturned = assertSuccessfulPost(path, lr.cookie, md, Metadata.class);
		
		assertTrue(mdReturned.getId() != null);
		assertTrue(mdReturned.getKey().equalsIgnoreCase("key1"));
		assertTrue(mdReturned.getValue().equalsIgnoreCase("value1"));
		
		JsonNode jn = apiCtx.getByIdMap(DataClass.class, "dataclass.pageview.id", dc.getId());
		
		DataClass newDC = objectMapper.treeToValue(jn,  DataClass.class);
		
		List<Metadata> mds = newDC.getMetadata();
		assertTrue(mds.size() == 1);
		assertTrue(mds.get(0).getKey().equalsIgnoreCase("key1"));
		assertTrue(mds.get(0).getValue().equalsIgnoreCase("value1"));
	}
	
	@FlywayTest(invokeCleanDB=true, invokeBaselineDB=true)
	@Test
	public void addChildElement() throws Exception {
		
		LoginResponse lr = doLogin();
		
		DataSet ds = DataSetApi.createDataSet(apiCtx, "Test Dataset", "This is just a test dataset", "Author", "Organization");
		DataClass dc = DataModelApi.newDataClass(apiCtx, ds, "class1", "class1desc");
		DataType dt = DataModelApi.newPrimitiveType(apiCtx, ds, "String data type", "data type description", "");

		String path = "/dataclass/newChildDataElement/" + dc.getId();
		
		DataClassService.NewDataElementDTO de = new DataClassService.NewDataElementDTO();
		de.setLabel("my test element");
		de.setDescription("my test element description");
		de.dataType = new DataTypeDTO();
		de.dataType.id = dt.getId();
		
		DataElement deReturned = assertSuccessfulPost(path, lr.cookie, de, DataElement.class);
		
		assertTrue(deReturned.getLabel().equalsIgnoreCase("my test element"));
		assertTrue(deReturned.getDescription().equalsIgnoreCase("my test element description"));
		assertTrue(deReturned.getId() != null);
		
		JsonNode jn = apiCtx.getByIdMap(DataClass.class, "dataclass.pageview.id", dc.getId());
		DataClass newDC = objectMapper.treeToValue(jn,  DataClass.class);
		List<DataElement> des = newDC.getChildDataElements();
		
		assertTrue(des.size() == 1);
		assertTrue(des.get(0).getLabel().equalsIgnoreCase("my test element"));
		assertTrue(des.get(0).getDescription().equalsIgnoreCase("my test element description"));
	}

	@FlywayTest(invokeCleanDB=true, invokeBaselineDB=true)
	@Test
	public void newChildClass() throws Exception {
		
		LoginResponse lr = doLogin();
		
		DataSet ds = DataSetApi.createDataSet(apiCtx, "Test Dataset", "This is just a test dataset", "Author", "Organization");
		DataClass oldDC = DataSetApi.newDataClass(apiCtx, ds, "my parent class", "my parent class description");

		DataClass newDC = new DataClass();
		newDC.setLabel("my test class");
		newDC.setDescription("my test class description");
		
		String path = "/dataclass/newChildDataClass/" + oldDC.getId();
		
		DataClass dcReturned = assertSuccessfulPost(path, lr.cookie, newDC, DataClass.class);
		
		assertTrue(dcReturned.getId() != null);
		assertTrue(dcReturned.getLabel().equalsIgnoreCase("my test class"));
		assertTrue(dcReturned.getDescription().equalsIgnoreCase("my test class description"));
		
		JsonNode jn = apiCtx.getByIdMap(DataClass.class, "dataclass.pageview.id", oldDC.getId());
		oldDC = objectMapper.treeToValue(jn, DataClass.class);
		
		List<DataClass> childDataClasses = oldDC.getChildDataClasses();

		assertTrue(childDataClasses.size() == 1);
		assertTrue(childDataClasses.get(0).getLabel().equalsIgnoreCase("my test class"));
		assertTrue(childDataClasses.get(0).getDescription().equalsIgnoreCase("my test class description"));
	}
	
}

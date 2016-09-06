package ox.softeng.metadatacatalogue.test.restapi;

import static org.junit.Assert.assertTrue;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import ox.softeng.metadatacatalogue.api.DataModelApi;
import ox.softeng.metadatacatalogue.api.DataSetApi;
import ox.softeng.metadatacatalogue.domain.core.DataClass;
import ox.softeng.metadatacatalogue.domain.core.DataModel;
import ox.softeng.metadatacatalogue.domain.core.DataSet;
import ox.softeng.metadatacatalogue.domain.core.Metadata;
import ox.softeng.metadatacatalogue.restapi.transport.ResponseDTO;

public class DataModelServiceIT extends APITest {

	@FlywayTest(invokeCleanDB=true, invokeBaselineDB=true)
	@Test
	public void addMetadata() throws Exception {
		
		Response response = doLogin();
		String sessionId = getSessionCookie(response);
		
		
		DataSet ds = DataSetApi.createDataSet(apiCtx, "Test Dataset", "This is just a test dataset", "Author", "Organization");
		System.err.println(ds.getId());
		
		String path = "/datamodel/addMetadata/" + ds.getId();
		WebTarget resource = target.path(path);
		Invocation.Builder invocationBuilder = resource.request(MediaType.APPLICATION_JSON).cookie("JSESSIONID", sessionId);
		
		Metadata md = new Metadata();
		md.setKey("key1");
		md.setValue("value1");
		String json = objectMapper.writeValueAsString(md);
		System.err.println(json);
		response = invocationBuilder.post(Entity.entity(json, MediaType.APPLICATION_JSON));
		assertTrue(response.getStatus()==200);
		
		ResponseDTO respObj = (ResponseDTO) response.readEntity(ResponseDTO.class);
		assertTrue(respObj.isSuccess());
		assertTrue(respObj.getErrorMessages() == null || respObj.getErrorMessages().size() == 0);
		assertTrue(respObj.getReturnObjectType().contains("Metadata"));
		JsonNode jn1 = respObj.getReturnObject();
		String key1 = jn1.get("key").asText();
		String value1 = jn1.get("value").asText();
		assertTrue(key1.equalsIgnoreCase("key1"));
		assertTrue(value1.equalsIgnoreCase("value1"));
		String newId = jn1.get("id").asText();
		assertTrue(newId != null && !newId.equals(""));
		
		JsonNode jn = apiCtx.getByIdMap(DataModel.class, "datamodel.pageview.id", ds.getId());
		ArrayNode an = (ArrayNode) jn.get("metadata");
		
		assertTrue(an.size() == 1);
		String key = an.get(0).get("key").asText();
		String value = an.get(0).get("value").asText();
		assertTrue(key.equalsIgnoreCase("key1"));
		assertTrue(value.equalsIgnoreCase("value1"));

	}

	@FlywayTest(invokeCleanDB=true, invokeBaselineDB=true)
	@Test
	public void newChildDataClass() throws Exception {
		
		Response response = doLogin();
		String sessionId = getSessionCookie(response);
		
		DataSet ds = DataSetApi.createDataSet(apiCtx, "Test Dataset", "This is just a test dataset", "Author", "Organization");
		System.err.println(ds.getId());
		
		String path = "/datamodel/newChildDataClass/" + ds.getId();
		WebTarget resource = target.path(path);
		Invocation.Builder invocationBuilder = resource.request(MediaType.APPLICATION_JSON).cookie("JSESSIONID", sessionId);

		DataClass dc = new DataClass();
		dc.setLabel("my test class");
		dc.setDescription("my test class description");
		
		String json = objectMapper.writeValueAsString(dc);
		System.err.println(json);
		response = invocationBuilder.post(Entity.entity(json, MediaType.APPLICATION_JSON));
		assertTrue(response.getStatus()==200);
		
		ResponseDTO respObj = (ResponseDTO) response.readEntity(ResponseDTO.class);
		assertTrue(respObj.isSuccess());
		assertTrue(respObj.getErrorMessages() == null || respObj.getErrorMessages().size() == 0);
		assertTrue(respObj.getReturnObjectType().contains("DataClass"));
		JsonNode jn1 = respObj.getReturnObject();
		String label1 = jn1.get("label").asText();
		String description1 = jn1.get("description").asText();
		assertTrue(label1.equalsIgnoreCase("my test class"));
		assertTrue(description1.equalsIgnoreCase("my test class description"));
		String newId = jn1.get("id").asText();
		assertTrue(newId != null && !newId.equals(""));
		
		JsonNode jn = apiCtx.getByIdMap(DataModel.class, "datamodel.pageview.id", ds.getId());
		ArrayNode an = (ArrayNode) jn.get("childDataClasses");
		
		assertTrue(an.size() == 1);
		String label = an.get(0).get("label").asText();
		String description = an.get(0).get("description").asText();
		assertTrue(label.equalsIgnoreCase("my test class"));
		assertTrue(description.equalsIgnoreCase("my test class description"));

	}
	
}

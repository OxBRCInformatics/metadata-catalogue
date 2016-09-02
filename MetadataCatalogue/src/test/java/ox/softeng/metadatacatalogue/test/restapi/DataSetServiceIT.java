package ox.softeng.metadatacatalogue.test.restapi;

import static org.junit.Assert.assertTrue;

import java.util.UUID;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import ox.softeng.metadatacatalogue.domain.core.DataSet;
import ox.softeng.metadatacatalogue.restapi.transport.ResponseDTO;

public class DataSetServiceIT extends APITest {

	@FlywayTest(invokeCleanDB=true, invokeBaselineDB=true)
	@Test
	public void createDataSet() throws Exception {
		
		Response response = doLogin();
		String sessionId = getSessionCookie(response);
		
		WebTarget resource = target.path("/dataset/create/");
		Invocation.Builder invocationBuilder = resource.request(MediaType.APPLICATION_JSON).cookie("JSESSIONID", sessionId);
		
		DataSet ds = new DataSet();
		ds.setLabel("Dataset 1");
		ds.setDescription("description 1");
		ds.setAuthor("author 1");
		ds.setOrganization("organisation 1");
		String json = objectMapper.writeValueAsString(ds);
		response = invocationBuilder.post(Entity.entity(json, MediaType.APPLICATION_JSON));

		assertTrue(response.getStatus()==200);
		ResponseDTO respObj = (ResponseDTO) response.readEntity(ResponseDTO.class);
		assertTrue(respObj.isSuccess());
		assertTrue(respObj.getErrorMessages() == null || respObj.getErrorMessages().size() == 0);
		assertTrue(respObj.getReturnObjectType().contains("DataSet"));

		JsonNode jn1 = respObj.getReturnObject();
		String label = jn1.get("label").asText();
		String id = jn1.get("id").asText();
		assertTrue(label.equalsIgnoreCase("Dataset 1"));
		
		
		JsonNode jn = apiCtx.getByIdMap(DataSet.class, "datamodel.pageview.id", UUID.fromString(id));
		String l = jn.get("label").asText();
		assertTrue(l.equalsIgnoreCase("Dataset 1"));

	}

	
}

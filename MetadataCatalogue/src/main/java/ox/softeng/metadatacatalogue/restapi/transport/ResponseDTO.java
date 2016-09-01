package ox.softeng.metadatacatalogue.restapi.transport;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.databind.JsonNode;

@XmlRootElement
public class ResponseDTO {

	boolean success;
	
	List<String> errorMessages;
	
	String returnObjectType;
	
	JsonNode returnObject;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<String> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}

	public String getReturnObjectType() {
		return returnObjectType;
	}

	public void setReturnObjectType(String returnObjectType) {
		this.returnObjectType = returnObjectType;
	}

	public JsonNode getReturnObject() {
		return returnObject;
	}

	public void setReturnObject(JsonNode returnObject) {
		this.returnObject = returnObject;
	}
	
	
}
